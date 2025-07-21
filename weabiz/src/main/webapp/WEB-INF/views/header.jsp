<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 상대 경로로 외부 CSS 적용 -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/header.css">
</head>
<body>
	<header>
		<div class="logo">
			<a href="/home">WeABiz</a>
		</div>
		<div class="user-info">
			<c:if test="${not empty user}">
				<div class="user-info">
					<span>안녕하세요, ${user.userName}님!</span> <span><a
						href="/logout">로그아웃</a></span>
				</div>

			</c:if>
		</div>
	</header>

	<nav>
		<ul>
			<li>게시판
				<ul class="submenu">
					<li><a href="/board">게시판</a></li>
				</ul>
			</li>
			<li>메일
				<ul class="submenu">
					<li><a href='/userEmail'>사내 메일</a></li>
				</ul>
			</li>
			<li>일정/캘린더
				<ul class="submenu">
					<li><a href='/userTodoList'>일정 관리</a></li>
					<li><a href='/userSchedule'>스케줄 관리</a></li>
				</ul>
			</li>
			<li>AI 업무지원
				<ul class="submenu">
					<li><a href='/aiMeeting'>회의록 자동 생성</a></li>
					<li><a href='/aiChat'>AI 질문 응답</a></li>
				</ul>
			</li>
			<li>문서 관리
				<ul class="submenu">
					<li>준비중입니다.</li>
				</ul>
			</li>
			<c:if test="${role eq 'ADMIN'}">
				<li>관리자 설정
					<ul class="submenu">
						<li><a href="/user">유저 관리</a></li>
						<li><a href="/department">부서 관리</a></li>
						<li><a href="/schedule">전체 스케줄</a></li>
					</ul>
				</li>
			</c:if>
		</ul>
	</nav>
</body>
</html>