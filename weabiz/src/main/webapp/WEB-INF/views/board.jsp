<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>

<!-- CSS 적용 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boardSidebar.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board.css">

</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="layout">
        <!-- 사이드바 -->
        <jsp:include page="/WEB-INF/views/boardSidebar.jsp" />

        <!-- 본문 콘텐츠 -->
        <div class="content" style="flex: 1; padding: 30px;">
            <h2>📋 ${boardType}</h2>
            <a href="/board/insert" class="write-btn">✏️ 글쓰기</a>

            <table class="board-table" border="0">
                <thead>
                    <tr>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="board" items="${boardList}">
                        <tr>
                            <td><a href="/board/${board.boardId}">${board.title}</a></td>
                            <td>${board.writer}</td>
                            <td>${board.regdate}</td> <!-- 날짜 포맷 필요시 fmt 사용 -->
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
