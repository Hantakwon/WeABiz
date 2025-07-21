<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>부서 목록</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/departmentList.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />
	<div class="container">
		<h2>📋 부서 목록</h2>
		<a class="btn"
			href="${pageContext.request.contextPath}/department/insert">➕ 부서
			추가</a>
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>부서명</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dept" items="${departments}">
					<tr>
						<td>${dept.deptId}</td>
						<td>${dept.deptName}</td>
						<td>
							<button class="btn delete"
								onclick="confirmDelete(${dept.deptId})">삭제</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<script>
    function confirmDelete(id) {
        if (confirm('정말 삭제하시겠습니까?')) {
            location.href = '${pageContext.request.contextPath}/department/delete/' + id;
        }
    }
	</script>
</body>
</html>