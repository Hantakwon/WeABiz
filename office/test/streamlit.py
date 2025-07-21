import streamlit as st
import test.AI as AI
st.title("간단한 채팅 인터페이스")

# 채팅 메시지 저장
if "messages" not in st.session_state:
    st.session_state.messages = []

# 채팅 메시지 표시
for message in st.session_state.messages:
    with st.chat_message(message["role"]):
        st.markdown(message["content"])

# 사용자 입력 처리
if prompt := st.chat_input("메시지를 입력하세요."):
    st.session_state.messages.append({"role": "user", "content": prompt})
    with st.chat_message("user"):
        st.markdown(prompt)

    # 응답 생성 (여기서는 간단한 예시로 입력한 메시지를 그대로 출력)
    response = AI.llm.invoke(prompt).content

    st.session_state.messages.append({"role": "assistant", "content": response})
    with st.chat_message("assistant"):
        st.markdown(response)