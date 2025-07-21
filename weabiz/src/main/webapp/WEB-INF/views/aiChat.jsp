<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>AI 부서 상담</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/aiChat.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="container">
		<h2>💬 부서별 AI 챗봇 상담</h2>

		<input type="hidden" id="dept" value="${deptName}">

		<div id="chat"></div>

		<label for="question">질문 입력:</label>
		<textarea id="question" rows="3" placeholder="궁금한 점을 입력해 주세요."></textarea>
		<br>
		<button onclick="sendQuestion()">질문 보내기</button>
	</div>

	<script>
        function escapeHtml(text) {
            return text
                .replace(/&/g, "&amp;")
                .replace(/</g, "&lt;")
                .replace(/>/g, "&gt;");
        }

        function loadWelcomeMessage() {
            const dept = document.getElementById("dept").value;
            const chatArea = document.getElementById("chat");
            chatArea.innerHTML = ''; // reset chat

            const welcome = "안녕하세요! " + dept + " 챗봇입니다. 무엇이든 물어보세요.";
            chatArea.innerHTML = '<div class="message" data-role="assistant">' + welcome + '</div>';
        }

        async function sendQuestion() {
            const question = document.getElementById("question").value.trim();
            const dept = document.getElementById("dept").value;
            const chatArea = document.getElementById("chat");

            if (!question) {
                alert("질문을 입력하세요.");
                return;
            }

            const history = [];

            document.querySelectorAll(".message").forEach(div => {
                history.push({
                    role: div.dataset.role,
                    content: div.textContent
                });
            });

            history.push({ role: "user", content: question });

            try {
                const response = await fetch("/ask", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ question, dept, history })
                });

                const data = await response.json();

                chatArea.innerHTML += '<div class="message" data-role="user">' + escapeHtml(question) + '</div>';
                chatArea.innerHTML += '<div class="message" data-role="assistant">' + escapeHtml(data.answer).replace(/\n/g, "<br>") + '</div>';

                document.getElementById("question").value = "";
                chatArea.scrollTop = chatArea.scrollHeight;
            } catch (error) {
                console.error("에러 발생:", error);
                alert("서버 오류가 발생했습니다.");
            }
        }

        window.onload = loadWelcomeMessage;
    </script>
</body>
</html>
