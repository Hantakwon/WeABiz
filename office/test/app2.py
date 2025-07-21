
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.staticfiles import StaticFiles
# from .routers import character_api, monster_api
import test.AI as AI, Prompt

app = FastAPI()

"""
app.include_router(
    character_api.router,                   
    prefix="/character",
    tags=["character"])

app.include_router(
    monster_api.router,                   
    prefix="/monster",
    tags=["monster"])   
"""

# CORS 미들웨어 추가
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 모든 도메인에서 접근 허용
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"],  # 모든 헤더 허용
)

"""
# 정적 파일 제공 설정
app.mount("/static", StaticFiles(directory="static"), name="static")
"""

@app.get("/")
async def home():
    return {
        "message" : "Welcome to FastAPI & Gemini!"
    }

@app.post("/board/summarize")
async def board_summary(prompt: str):
    response = Prompt.chain.invoke({"prompt": prompt})
    return {
        "result": response.content
    }