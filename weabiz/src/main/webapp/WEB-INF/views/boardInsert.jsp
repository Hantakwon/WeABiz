<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성</title>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Summernote -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>

<!-- 기본 스타일 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/boardInsert.css">

<script>
	$(function() {
		$('#summernote').summernote({
			height : 300,
			lang : 'ko-KR',
			placeholder : '내용을 입력하세요'
		});
	});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="container">
		<h2>✏️ 글 작성</h2>
		<form action="/board/insert" method="post" enctype="multipart/form-data">
			<label for="title">제목</label>
			<input type="text" id="title" name="title" placeholder="제목을 입력하세요" required>

			<label for="summernote">내용</label>
			<div style="margin-top: 8px;">
				<textarea id="summernote" name="content"></textarea>
			</div>
			
			<c:if test="${role eq 'ADMIN'}">
				<div class="checkbox-area">
					<input type="checkbox" id="isNotice" name="isNotice">
					<label for="isNotice">공지사항으로 등록</label>
				</div>
			</c:if>
	
			<label for="file">파일 첨부</label>
			<input type="file" name="files" id="file" multiple>

			<button type="submit">작성 완료</button>
		</form>
	</div>
</body>
</html>
