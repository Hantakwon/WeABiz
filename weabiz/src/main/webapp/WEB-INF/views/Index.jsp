<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CS 프로그램 로그인</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/index.css">
<style>
</style>
</head>
<body>
	<div class="login-container">
		<h1>WeABiz</h1>
		<form action="/login" method="POST">
			<label for="email">이메일</label> <input type="email" id="email"
				name="email" required> <label for="password">비밀번호</label> <input
				type="password" id="password" name="password" required>

			<button type="submit">로그인</button>
		</form>


		<div class="footer">ⓒ 2025 CS Corporation</div>
	</div>
</body>
</html>