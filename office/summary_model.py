# summary_model.py

def summarize_text(text: str) -> str:
    # 여기엔 실제 요약 모델 또는 API 연동 로직을 넣을 수 있습니다
    if len(text.strip()) < 20:
        return "요약할 내용이 충분하지 않습니다."

    # 예시 요약 방식 (앞부분만 자르기)
    return text[:200] + "..."