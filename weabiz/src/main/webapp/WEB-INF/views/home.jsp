<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/main.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/home.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="container">
		<!-- 공지사항 -->
		<div id="notice">
			<c:if test="${not empty latestNotice}">
				<p>
					📢 [공지] <a href="/board/${latestNotice.boardId}">${latestNotice.title}</a>
				</p>
			</c:if>
		</div>

		<!-- TODO & Calendar 영역 -->
		<div class="main-content">
			<div id="todolist">
				<div class="todo-header">
					<h3>✅ TODO</h3>
					<a href="/userTodoList">수정하기</a>
				</div>
				<c:if test="${empty todoList}">
					<p>할 일이 없습니다.</p>
				</c:if>
				<c:forEach var="todo" items="${todoList}">
					<div class="todo-item">
						<form action="/userTodoList/complete" method="post">
							<input type="hidden" name="todoId" value="${todo.todoId}" /> <label>
								<input type="checkbox" name="completed"
								onchange="this.form.submit()"
								<c:if test="${todo.completed}">checked</c:if> /> <span
								class="${todo.completed ? 'completed' : ''}">${todo.content}</span>
							</label>
						</form>
					</div>
				</c:forEach>
			</div>

			<div id="calendar-container">
				<div id="calendar"></div>
			</div>

		</div>
	</div>

	<script>
		document.addEventListener('DOMContentLoaded', function () {
			const calendarEl = document.getElementById('calendar');
	
			const calendar = new FullCalendar.Calendar(calendarEl, {
				initialView: 'listWeek',
				locale: 'ko',
				displayEventTime: false,
	
				events: function (info, successCallback, failureCallback) {
					Promise.all([
						fetch('/userSchedule/events').then(res => res.json()),
						fetch('/schedule/events').then(res => res.json())
					])
					.then(([userEvents, generalEvents]) => {
						// 유저 일정은 초록색, 전체 일정은 파란색
						const styledUserEvents = userEvents.map(e => ({ ...e, color: '#28a745' }));
						const styledGeneralEvents = generalEvents.map(e => ({ ...e, color: '#4dabf7' }));
						successCallback([...styledUserEvents, ...styledGeneralEvents]);
					})
					.catch(failureCallback);
				}
			});
	
			calendar.render();
		});
	</script>

</body>
</html>