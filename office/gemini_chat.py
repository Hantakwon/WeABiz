import os
import requests
from dotenv import load_dotenv

load_dotenv()
API_KEY = os.getenv("GEMINI_API_KEY")
MODEL_NAME = "gemini-1.5-flash"

def ask_gemini(question: str, context: str, history=None) -> str:
    url = f"https://generativelanguage.googleapis.com/v1/models/{MODEL_NAME}:generateContent?key={API_KEY}"
    headers = { "Content-Type": "application/json" }

    contents = []

    if history:
        for msg in history:
            role = msg.role  # ✅ 수정
            content = msg.content  # ✅ 수정
            if role == "user":
                contents.append({ "role": "user", "parts": [{ "text": content }] })
            elif role == "assistant":
                contents.append({ "role": "model", "parts": [{ "text": content }] })

    # 현재 질문 + context 추가
    user_prompt = f"""다음 맥락을 참고하여 질문에 한국어로 대답해 주세요.

[맥락]
{context}

[질문]
{question}

[답변은 반드시 한국어로 하세요.]"""

    contents.append({ "role": "user", "parts": [{ "text": user_prompt }] })

    payload = {
        "contents": contents
    }

    response = requests.post(url, headers=headers, json=payload)
    response.raise_for_status()
    return response.json()["candidates"][0]["content"]["parts"][0]["text"]

def summarize_text(text: str) -> str:
    url = f"https://generativelanguage.googleapis.com/v1/models/{MODEL_NAME}:generateContent?key={API_KEY}"
    
    headers = { "Content-Type": "application/json" }
    prompt = f"""다음 텍스트를 간결하게 요약해 주세요. 반드시 한국어로 대답하세요.

[텍스트]
{text}

[요약]
"""
    
    payload = {
        "contents": [
            {
                "parts": [
                    {
                        "text": prompt
                    }
                ]
            }
        ]
    }

    response = requests.post(url, headers=headers, json=payload)
    response.raise_for_status()
    return response.json()["candidates"][0]["content"]["parts"][0]["text"]