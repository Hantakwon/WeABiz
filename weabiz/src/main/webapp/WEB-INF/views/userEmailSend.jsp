<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일 작성</title>

<!-- 공통 사이드바 및 레이아웃 스타일 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/emailSidebar.css">
<!-- 메일 작성 전용 스타일 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/emailSend.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="layout">
		<jsp:include page="/WEB-INF/views/mailSidebar.jsp" />

		<div class="container">
			<h2>📨 메일 작성</h2>
			<form action="/userEmail/send" method="post">
				<label for="receiverEmail">받는 사용자 email</label> <input type="text"
					id="receiverEmail" name="receiverEmail"
					placeholder="example@example.com" required>

				<c:if test="${not empty errorMessage}">
					<div class="error-message">${errorMessage}</div>
				</c:if>

				<label for="title">제목</label> <input type="text" id="title"
					name="title" placeholder="제목을 입력하세요" required> <label
					for="content">내용</label>
				<textarea id="content" name="content" placeholder="메일 내용을 입력하세요…"
					rows="8" required></textarea>

				<button type="submit">메일 보내기</button>
			</form>

		</div>
	</div>
</body>
</html>
