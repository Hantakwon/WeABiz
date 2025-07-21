<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이메일 확인</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/emailSidebar.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/emailDetail.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="layout">
		<jsp:include page="/WEB-INF/views/mailSidebar.jsp" />

		<div class="container">
			<div class="email-detail">
				<h2>${email.title}</h2>

				<div class="email-info">
					<p><strong>보낸 사람:</strong> ${email.sender.userName} (${email.sender.userName})</p>
					<p><strong>받은 시간:</strong> ${email.sentAt}</p>
					<p><strong>읽음 여부:</strong>
						<c:choose>
							<c:when test="${email.read}">읽음</c:when>
							<c:otherwise>안 읽음</c:otherwise>
						</c:choose>
					</p>
				</div>

				<div class="email-content">${email.content}</div>

				<div class="back-link">
					<a href="/userEmail">← 받은 메일함으로 돌아가기</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
