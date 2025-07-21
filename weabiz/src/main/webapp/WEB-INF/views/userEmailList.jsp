<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>받은 메일함</title>

<!-- 공통 레이아웃/사이드바 CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/emailSidebar.css">

<!-- 메일 리스트 전용 CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/emailList.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />
	
	<div class="layout">
		<jsp:include page="/WEB-INF/views/mailSidebar.jsp" />

		<div class="container">
			<div class="mail-header">
				<h2>📥 받은 메일함</h2>
				<a href='/userEmail/send' class="send-btn">메일 작성</a>
			</div>

			<table>
				<thead>
					<tr>
						<th>제목</th>
						<th>보낸 사람</th>
						<th>날짜</th>
						<th>읽음 여부</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="mail" items="${emails}">
						<tr class="${!mail.read ? 'unread' : ''}">
							<td><a href="/userEmail/${mail.emailId}">${mail.title}</a></td>
							<td>${mail.sender.userName}</td>
							<td>${mail.sentAt}</td>
							<td><c:choose>
									<c:when test="${mail.read}">읽음</c:when>
									<c:otherwise>안 읽음</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
