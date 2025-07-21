import numpy as np

from transformers import AutoTokenizer
#from langchain_community.document_loaders import WikipediaLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_community.embeddings import HuggingFaceEmbeddings
from langchain_community.vectorstores import FAISS
from langchain_community.vectorstores.utils import DistanceStrategy
from sentence_transformers import CrossEncoder

from langchain_community.llms import Ollama
#from langchain_community.chat_models import ChatOllama
from langchain_core.messages import HumanMessage, AIMessage
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

import pdfplumber, warnings
from queue import Queue
#from openai import OpenAI

def get_all_queue_element(que) :
    result_str = ""
    while not que.empty():
        result_str += (que.get() + "\n")
    return result_str
#===========================================================================================
warnings.simplefilter(action='ignore', category=FutureWarning) # warning message 안나오게

SHOW_INSIDE = False

# [1] Load PDF documents ------------------------------------------
class Document:
    def __init__(self, page_content, metadata=None):
        self.page_content = page_content
        self.metadata = metadata or {}      # text_splitter가 metadata를 기본 필요로 함
        
pdf_paths = ['pdf/2024 공무원 인사실무.pdf']

pages_text = []
for pdf_path in pdf_paths:
    with pdfplumber.open(pdf_path) as pdf:
        for page in pdf.pages:
            page_text = page.extract_text()
            if page_text:  # Ensure there is text on the page
                doc = Document(page_text)
                pages_text.append(doc)
            
# [2.1] text splitter 정의 --------------------------------------
text_splitter = RecursiveCharacterTextSplitter.from_huggingface_tokenizer(
    tokenizer=AutoTokenizer.from_pretrained("sentence-transformers/all-MiniLM-L12-v2"),
    chunk_size=256,     # 256 chars / chunk
    chunk_overlap=16,   # 이웃하는 chunk사이 16 chars overlap
    strip_whitespace=True,  # chunk 생성시에 공백 제거
)
# [2.2]불러온 페이지에 대하여 text_splitting하여 chunks 구하기
chunks = text_splitter.split_documents(pages_text)

if SHOW_INSIDE :
    print ('\nsplit된 chunk의 갯수: ',len(chunks))
    print('\nchunk[0]의 전체내용: \n',chunks[0])
    print('\nchunk[0]의 page_content: \n',chunks[0].page_content)

# [3] bidirectional encpoder 모델을 이용한 Embedding 정의 ----------------
bi_encoder = HuggingFaceEmbeddings(
    model_name="sentence-transformers/all-MiniLM-L12-v2", model_kwargs={"device": "cpu"}
    # model_name="sentence-transformers/all-MiniLM-L12-v2", model_kwargs={"device": "cuda"}
)

# [4] chunks들로부터 bi_encoder를 이용한 임베딩을 사용하고, dot-product거리를 이용하여 faiss index 생성. 
# FAISS (Facebook AI Similarity Search) 
faiss_db = FAISS.from_documents(
    chunks, bi_encoder, distance_strategy=DistanceStrategy.DOT_PRODUCT
)

# [5] cross encoder 정의 -------------------------------------------------
cross_encoder = CrossEncoder(
    "cross-encoder/ms-marco-TinyBERT-L-2-v2", max_length=768, device="cpu"
)
prompt0 = "당신은 공무원 인사실무 관련 PDF 문서를 기반으로 질문에 답하는 AI 도우미입니다."

prompt2 = '''당신은 PDF 문서에서 필요한 정보를 찾아 정확하게 설명해주는 역할을 합니다.

            사용자는 공무원 인사실무에 대한 질문을 자연어로 입력합니다.

            당신은 관련 문서를 검색한 뒤, 해당 질문에 대한 가장 적절한 내용을 근거로 명확하고 간결하게 설명합니다.

            문서에 근거하지 않은 정보는 제공하지 않으며, 문서에 기반한 사실만 전달합니다.

            문서의 표현을 가능한 한 유지하면서도 이해하기 쉬운 형식으로 요약해 설명해 주세요.

            출력은 오직 하나의 완성된 답변 문장(또는 문단)으로 제공하며, 문서 출처나 인용 표시는 하지 않습니다.'''


prompt_template = ChatPromptTemplate.from_messages (
    [
        ("system", "{system_prompt}"),
        ("human", "{input}"),
        #MessagesPlaceholder(variable_name='chat_history'),
    ]
)
    # chat 기록 저장용 리스트
chat_history = []
    # llm 설정
llm = Ollama(model="gemma2:2b",temperature=0.0)       # llama3:70b-text
#llm = ChatOllama(model="llama3:Text",temperature=0.0)       
    # chain 설정
chain = prompt_template | llm

TOP_CHUNK_NO = 1
ANSWER_SCORE_THRESH = 2
chat_queue = Queue(maxsize = 4)

#===========================================================================================
while True :
    
    print("-"*80)
    question = input("User ['q' to quit] : ")
    
    q = question[:]
    if q.strip().lower() == 'q' : break
    elif  q == '' : continue
           
    user_prompt = get_all_queue_element(chat_queue) + question
    print("user prompt : ",user_prompt)
        
    # [5] question과 유사도가 가장 가까운 상위 5개의 chunk를 추출
    retrieved_chunks = faiss_db.similarity_search(user_prompt, k=5)

    reranked_chunks = cross_encoder.rank(
        (user_prompt),
        [doc.page_content for doc in retrieved_chunks],
        top_k=TOP_CHUNK_NO,
        return_documents=True,
    )
    answer_score = reranked_chunks[0]['score']
    print(f"\n\n*** 최종 상위 답변 {TOP_CHUNK_NO}개 문장의 corpus_id, score  : \n")
    for doc in reranked_chunks:
        print('CORPUS_ID: ', doc['corpus_id'], ' SCORE: ', doc['score'])
    print("-"*80)

    context = "".join(doc["text"] + "\n\n" for doc in reranked_chunks)

    print("\n*** 최종 상위 답변 문장의 TEXT  : \n",reranked_chunks[0]['text'])

    if SHOW_INSIDE :
        print(f"\n*** RAG에 의한 연관된 상위 {len(reranked_chunks)} 개 chunks")
        for doc in reranked_chunks:
            corpus_id = doc["corpus_id"]
            print(f"Chunk {corpus_id} ")
         
    #-----------------------------------------------------------------------------------------   
    try :
        
        if answer_score <= ANSWER_SCORE_THRESH :    # vector store의 유사도 검색결과값이 낮은 경우
            # 새로 대화 시작                        # LLM이 대땁하도록 함
            print("n**** New Conversation Starting ***\n")
            with chat_queue.mutex:
                chat_queue.queue.clear()
            chat_history = []
            response = chain.invoke({"input":question, "chat_history":chat_history, "system_prompt":prompt0})
            # 주어진 vector store의 데이터로 대답을 하도록 함
        else :
            response = chain.invoke({"input":"user's sentence:"+ question + "\ntextbook_sentences: "+context, \
                "chat_history":chat_history, "system_prompt":prompt2})
            #response = chain.invoke({"input":"user's sentence:"+ question + "\ntextbook_sentences: "+context, \
            #         "system_prompt":prompt2})
        
        print("-"*80,'\n')
        print("*** user_prompt: ",user_prompt,'\n')
        #print("*** AI: ",response.content,'\n')    # in case of ChatOllama: content, response_metadata, id
        print("*** AI: ",response,'\n')             
        
        chat_history.append(HumanMessage(content=question))
        chat_history.append(AIMessage(content=response))
        
        if (chat_queue.empty()) :
            chat_queue.put(question)
        elif (chat_queue.full()) :
            chat_queue.get()
            
        if answer_score <= ANSWER_SCORE_THRESH :
            pass            
        else :
            if '"' and '\n' in response :
                answer = response[response.find('"'):response.rfind('"')+1]
            else :
                answer = response[:]
            print("answer: ",answer)
            chat_queue.put(answer)
            #chat_queue.put(response.content)   # ChatOllama
        
    except Exception as e:
        print("Error : ",e)