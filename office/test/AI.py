import os
from dotenv import load_dotenv
from langchain_google_genai import ChatGoogleGenerativeAI

# .env 파일에서 환경 변수 불러오기
load_dotenv()

API_KEY = os.getenv("API_KEY")

# LLM 객체 생성
llm = ChatGoogleGenerativeAI(
    model="gemini-2.0-flash-lite",
    temperature=0,
    api_key=API_KEY
)