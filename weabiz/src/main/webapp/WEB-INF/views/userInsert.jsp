<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>유저 추가</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/userInsert.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="container">
		<h2>👤 유저 등록</h2>
		<form action="/user/insert" method="post" enctype="multipart/form-data">
			<label for="userName">이름</label>
			<input type="text" name="userName" id="userName" required>

			<label for="password">비밀번호</label>
			<input type="password" name="password" id="password" required>

			<label for="email">이메일</label>
			<input type="email" name="email" id="email">

			<label for="phoneNumber">전화번호</label>
			<input type="text" name="phoneNumber" id="phoneNumber">

			<label for="deptId">부서</label>
			<select name="deptId" id="deptId">
				<option value="">-- 부서 선택 --</option>
				<c:forEach var="dept" items="${deptList}">
					<option value="${dept.deptId}">${dept.deptName}</option>
				</c:forEach>
			</select>

			<label for="position">직책</label>
			<input type="text" name="position" id="position">

			<label for="joined">입사일</label>
			<input type="date" name="joined" id="joined">

			<button type="submit">등록</button>
		</form>
	</div>
</body>
</html>
