<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>유저 정보 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/userUpdate.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp" />

<div class="container">
    <h2>👤 유저 정보 수정</h2>

    <form action="/user/update/${user.userId}" method="post">
        <label for="userName">이름</label>
        <input type="text" id="userName" name="userName" value="${user.userName}" required>

        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" value="${user.password}" required>

        <label for="email">이메일</label>
        <input type="email" id="email" name="email" value="${user.email}">

        <label for="phoneNumber">전화번호</label>
        <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">

        <label for="deptId">부서</label>
        <select id="deptId" name="deptId">
            <option value="">-- 선택 --</option>
            <c:forEach var="dept" items="${deptList}">
                <option value="${dept.deptId}" ${dept.deptId == user.deptId ? 'selected' : ''}>${dept.deptName}</option>
            </c:forEach>
        </select>

        <label for="position">직책</label>
        <input type="text" id="position" name="position" value="${user.position}">

        <label for="status">상태</label>
        <input type="text" id="status" name="status" value="${user.status}">

        <label for="joined">입사일</label>
        <input type="date" id="joined" name="joined" value="${user.joined}">

        <label for="resigned">퇴사일</label>
        <input type="date" id="resigned" name="resigned" value="${user.resigned}">

        <button type="submit">수정 완료</button>
    </form>
</div>
</body>
</html>
