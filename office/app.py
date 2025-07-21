from fastapi import FastAPI, UploadFile, File, Request
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List
import whisper
import os

from gemini_chat import summarize_text  
from rag_pipeline import process_pdf_for_dept, answer_question_for_dept  

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"], 
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Whisper 모델 로드 (base/small/medium/large 등 가능)
model = whisper.load_model("base")

# 메시지 구조 정의
class Message(BaseModel):
    role: str
    content: str

# 질문 요청 구조 정의
class QuestionRequest(BaseModel):
    question: str
    dept: str  # 필수
    history: List[Message] = []

class SummaryRequest(BaseModel):
    prompt: str  # 게시판 요약에 쓸 텍스트

# RAG 기반 질문 응답 처리
@app.post("/ask")
async def ask(request_data: QuestionRequest):
    dept = request_data.dept
    msg = process_pdf_for_dept(dept)
    if msg:
        return {"answer": msg}

    answer = answer_question_for_dept(
        dept, request_data.question, history=request_data.history
    )
    return {"answer": answer}


# 음성 파일 업로드 및 요약
@app.post("/upload_audio")
async def upload_audio(file: UploadFile = File(...)):
    temp_path = f"temp_{file.filename}"

    with open(temp_path, "wb") as f:
        f.write(await file.read())

    try:
        result = model.transcribe(temp_path)
        transcript = result["text"]
        summary = summarize_text(transcript)
    finally:
        os.remove(temp_path)

    return {
        "transcript": transcript,
        "summary": summary
    }

@app.post("/board/summary")
async def summarize_board(request: Request):
    prompt = request.query_params.get("prompt")

    if not prompt:
        return {"answer": ["요약할 텍스트가 없습니다."]}

    summary_text = summarize_text(prompt)
    # ✅ 줄바꿈 기준으로 리스트로 변환
    summary_lines = summary_text.strip().split('\n')
    return summary_lines