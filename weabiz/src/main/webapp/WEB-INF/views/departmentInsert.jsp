<!-- departmentInsert.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부서 추가</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/departmentInsert.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />
	<div class="container">	
		<h2>➕ 부서 추가</h2>
		<form action="${pageContext.request.contextPath}/department/insert" method="post">
			<label for="deptName">부서 이름</label>
			<input type="text" name="deptName" id="name" required>

			<div class="btn-group">
				<button type="submit">등록</button>
				<button type="button" onclick="location.href='/department'">취소</button>
			</div>
		</form>
	</div>
</body>
</html>