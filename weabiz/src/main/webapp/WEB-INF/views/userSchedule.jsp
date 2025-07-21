<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스케줄 관리</title>
<link
	href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/main.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/userSchedule.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="container">
		<!-- 달력 + 입력 폼 -->
		<div class="calendar-form-section">
			<div id="calendar-section">
				<div id="calendar"></div>
			</div>

			<div class="form-area">
				<h3 class="form-title">📌 일정 추가</h3>
				<form action="/userSchedule/insert" method="post">
					<label for="title">제목</label> <input type="text" id="title"
						name="title" placeholder="제목을 입력하세요" required> <label
						for="content">내용</label> <input type="text" id="content"
						name="content" placeholder="내용을 입력하세요" required> <label
						for="start">시작일</label> <input type="date" id="start"
						name="startdate"> <label for="end">마감일</label> <input
						type="date" id="end" name="enddate">

					<button type="submit">작성 완료</button>
				</form>
			</div>
		</div>

		<!-- 일정 수정 테이블 -->
		<div class="table-area">
			<table class="schedule-table">
				<thead>
					<tr>
						<th>제목</th>
						<th>내용</th>
						<th>시작일</th>
						<th>마감일</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody id="schedule-body">
					<c:forEach var="schedule" items="${scheduleList}">
						<tr>
							<td><input type="text" name="title"
								value="${schedule.title}" form="form-${schedule.id}"></td>
							<td><input type="text" name="content"
								value="${schedule.content}" form="form-${schedule.id}">
							</td>
							<td><input type="date" name="startdate"
								value="${schedule.startdate}" class="startdate"
								form="form-${schedule.id}"></td>
							<td><input type="date" name="enddate"
								value="${schedule.enddate}" class="enddate"
								form="form-${schedule.id}"></td>
							<td>
								<form id="form-${schedule.id}"
									action="/userSchedule/update/${schedule.id}" method="post"
									class="schedule-update-form">
									<button type="submit">📝</button>
								</form>
							</td>
							<td>
								<button type="button" onclick="deleteSchedule(${schedule.id})">🗑️</button>
							</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>

	<script>
		document.addEventListener('DOMContentLoaded', function () {
			// 📅 FullCalendar
			const calendarEl = document.getElementById('calendar');
			const calendar = new FullCalendar.Calendar(calendarEl, {
				initialView: 'dayGridMonth',
				locale: 'ko',
				events: '/userSchedule/events',
				displayEventTime: false,
				eventColor: '#28a745'
			});
			calendar.render();

			// 🗑️ 삭제 기능
			window.deleteSchedule = function (id) {
				if (confirm("정말 삭제하시겠습니까?")) {
					const form = document.createElement('form');
					form.method = 'post';
					form.action = '/userSchedule/delete/' + id;
					document.body.appendChild(form);
					form.submit();
				}
			};

			// ➕ 일정 추가 폼: 시작일 → 마감일 제한
			const startInput = document.getElementById('start');
			const endInput = document.getElementById('end');

			if (startInput && endInput) {
				startInput.addEventListener('change', () => {
					const startDate = startInput.value;
					endInput.min = startDate;
					if (endInput.value && endInput.value < startDate) {
						endInput.value = startDate;
					}
				});
			}

			// 📝 수정 테이블 폼: 시작일 → 마감일 제한
			document.querySelectorAll('.schedule-update-form').forEach(form => {
				const startInput = form.querySelector('.startdate');
				const endInput = form.querySelector('.enddate');

				if (startInput && endInput) {
					startInput.addEventListener('change', () => {
						const startDate = startInput.value;
						endInput.min = startDate;
						if (endInput.value && endInput.value < startDate) {
							endInput.value = startDate;
						}
					});

					form.addEventListener('submit', e => {
						if (startInput.value && endInput.value && endInput.value < startInput.value) {
							alert("마감일은 시작일보다 빠를 수 없습니다.");
							e.preventDefault();
						}
					});
				}
			});
		});
	</script>
</body>
</html>
