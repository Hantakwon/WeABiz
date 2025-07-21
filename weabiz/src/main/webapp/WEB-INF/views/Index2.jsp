<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Calendar Page</title>

</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<a href="/board">게시판 이동</a>

	<form action="/login" method="post">
		<input type="text" name="userId" placeholder="아이디" required> <input
			type="password" name="password" placeholder="비멀번호" required>
		<button type="submit">로그인</button>
	</form>
	
</body>
</html>
