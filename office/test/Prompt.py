from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.prompts import ChatPromptTemplate
import test.AI as AI

# 요약용 프롬프트 템플릿 구성
prompt_template = ChatPromptTemplate.from_messages([
    ("system", "너는 한국 세계 최고의 인기 기자야. 다음 내용을 바탕으로 기사를 써줘."),
    ("human", "{prompt}")
])

# 프롬프트 + LLM 체인 구성
chain = prompt_template | AI.llm

