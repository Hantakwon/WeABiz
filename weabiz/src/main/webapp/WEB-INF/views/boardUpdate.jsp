<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>

    <!-- Summernote -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>

    <script>
        $(function() {
            $('#summernote').summernote({
                height: 300,
                lang: 'ko-KR',
                placeholder: '내용을 입력하세요'
            });
        });
    </script>
   
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/boardInsert.css">
	
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container">
        <h2>✏️ 게시글 수정</h2>
        <form action="/board/update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="boardId" value="${board.boardId}" />

            <label for="title">제목</label>
            <input type="text" id="title" name="title" value="${board.title}" required>

            <label for="content">내용</label>
            <textarea id="summernote" name="content">${board.content}</textarea>

            <!-- 기존 첨부파일 표시 -->
            <c:if test="${not empty board.attachments}">
                <label>기존 첨부파일</label>
                <ul>
                    <c:forEach var="file" items="${board.attachments}">
                        <li>
                            <a href="/board/download/${file.attachmentId}">${file.fileName}</a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>

            <!-- 새 파일 업로드 -->
            <label>📎 추가 첨부파일</label>
            <input type="file" name="files" multiple>

            <button type="submit">수정 완료</button>
        </form>
    </div>
</body>
</html>
