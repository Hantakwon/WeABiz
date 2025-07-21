<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Todo 목록 수정</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/userTodoList.css">

<script>
    let newTodoCount = 0;

    function addNewTodoRow() {
        newTodoCount++;
        const table = document.getElementById("todoTable").getElementsByTagName('tbody')[0];
        const newRow = table.insertRow(-1);

        const rowNumber = table.rows.length;
        newRow.innerHTML = `
            <td>${rowNumber}</td>
            <td><input type="text" name="newContents" class="todo-input" /></td>
            <td></td>
            <td><button type="button" class="delete-btn" onclick="deleteRow(this)">삭제</button></td>
        `;
    }

    function deleteExistingRow(button, todoId) {
        const row = button.closest("tr");
        row.style.display = "none";

        const deletedInput = document.createElement("input");
        deletedInput.type = "hidden";
        deletedInput.name = "deletedIds";
        deletedInput.value = todoId;

        document.getElementById("todoForm").appendChild(deletedInput);
        updateRowNumbers();
    }

    function deleteRow(button) {
        const row = button.closest("tr");
        row.remove();
        updateRowNumbers();
    }

    function updateRowNumbers() {
        const rows = document.querySelectorAll("#todoTable tbody tr");
        let index = 1;
        rows.forEach(row => {
            if (row.style.display !== "none") {
                row.cells[0].innerText = index++;
            }
        });
    }
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="container">
		<h2>✏️ TODO 목록 수정</h2>

		<form method="post" action="/userTodoList/updateAll" id="todoForm">
			<table class="todo-table" id="todoTable">
				<thead>
					<tr>
						<th>번호</th>
						<th>내용</th>
						<th>완료 여부</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="todo" items="${todoList}" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td><input type="hidden" name="todoIds"
								value="${todo.todoId}" /> <input type="text" name="contents"
								value="${todo.content}" class="todo-input" /></td>
							<td><input type="checkbox" name="completedValues"
								value="${todo.todoId}" ${todo.completed ? "checked" : ""} /></td>
							<td>
								<button type="button" class="delete-btn"
									onclick="deleteExistingRow(this, '${todo.todoId}')">삭제</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<div class="buttons">
				<button type="button" onclick="addNewTodoRow()" class="add-btn">➕
					할 일 추가</button>
				<button type="submit" class="save-btn">💾 전체 저장</button>
			</div>
		</form>
	</div>
</body>
</html>
