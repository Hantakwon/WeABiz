<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>유저 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/userList.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container">
        <div class="title">유저 목록</div>
        <a href="/user/insert">✏️ 유저추가</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>전화번호</th>
                    <th>부서 ID</th>
                    <th>직책</th>
                    <th>입사일</th>
                    <th>퇴사일</th>
                    <th>수정</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.userName}</td>
                        <td>${user.email}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.deptId}</td>
                        <td>${user.position}</td>
                        <td>${user.joined}</td>
                        <td>${user.resigned}</td>
                        <td>
                            <a href="/user/update/${user.userId}">✏️</a>
                        </td>
                        <td>
                            <button onclick="deleteUser(${user.userId})">🗑️</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script>
        function deleteUser(userId) {
            if (confirm("정말 이 유저를 삭제하시겠습니까?")) {
                const form = document.createElement("form");
                form.method = "post";
                form.action = "/user/delete/" + userId;
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>
