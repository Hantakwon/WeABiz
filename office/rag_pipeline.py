from fastapi import HTTPException
import os
from pdf_parser import extract_text_from_pdf
from vector_store import VectorStore
from gemini_chat import ask_gemini

vector_stores = {}  # 부서별 캐싱 저장소

def process_pdf_for_dept(dept: str) -> str | None:
    if dept in vector_stores:
        return None

    file_name = f"{dept}.pdf"

    # 여기서 os.path.exists를 실제 경로로 바꾸지 마세요
    # 대신 extract_text_from_pdf 내부에서 처리됨
    if not os.path.exists(os.path.join(os.path.dirname(__file__), "pdf", file_name)):
        return f"{dept} 부서 자료는 현재 준비 중입니다."

    text = extract_text_from_pdf(file_name)

    vs = VectorStore()
    vs.split_text(text)
    vs.create_index()
    vector_stores[dept] = vs
    return None


def answer_question_for_dept(dept: str, question: str, history=None):
    vs = vector_stores.get(dept)
    if not vs:
        return "해당 부서의 정보를 찾을 수 없습니다."

    contexts = vs.search(question, top_k=5)
    context_text = "\n---\n".join(contexts)

    return ask_gemini(question, context_text, history=history)
