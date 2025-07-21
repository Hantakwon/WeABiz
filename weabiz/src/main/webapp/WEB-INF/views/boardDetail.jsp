<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${board.title}</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/boardDetail.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="board-container">
		<h2>📝 ${board.title}</h2>
		<p class="meta-info">
			🖊 작성자: <strong>${board.writer}</strong> | 📅 작성일: <strong>${board.regdate}</strong>
		</p>
		<hr>

		<div class="board-content">
			<p>
				<strong>내용:</strong>
			</p>
			<div class="content-box">
				<c:out value="${board.content}" escapeXml="false" />
			</div>
		</div>

		<c:if test="${not empty attachments}">
			<div class="attachments">
				<p>
					<strong>📎 첨부파일:</strong>
				</p>
				<ul>
					<c:forEach var="file" items="${attachments}">
						<li><a href="/board/download/${file.attachmentId}">
								${file.fileName} (${file.fileSize} bytes) </a></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<hr>
		<div class="btn-group">
			<a href="/board" class="btn">🔙 목록으로</a> 
			<a href="/board/summarize/${board.boardId}" class="btn">📌 요약하기</a>
			<c:if test="${user.userName eq board.writer or role eq 'ADMIN'}">
				<a href="/board/update/${board.boardId}" class="btn">✏️ 수정</a>
				<a href="/board/delete/${board.boardId}" class="btn" onclick="return confirm('정말 삭제하시겠습니까?')">🗑 삭제</a>
			</c:if>
		</div>

		<c:if test="${not empty responseDO}">
			<hr>
			<h3>🧠 요약 내용</h3>
			<div class="summary-box">
				<c:forEach var="line" items="${responseDO.answer}">
					<p>${line}</p>
				</c:forEach>
			</div>
		</c:if>
	</div>
</body>
</html>
