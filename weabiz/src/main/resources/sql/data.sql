INSERT INTO DEPT (DEPTNAME)
VALUES 
('인사과'),
('총무과'),
('재무팀'),
('영업부'),
('개발팀'),
('디자인팀'),
('고객지원팀');

INSERT INTO USERS (PASSWORD, USERNAME, EMAIL, PHONE_NUMBER, DEPTID, POSITION, STATUS, JOINED, CREATED_AT)
VALUES 
('pass123', '홍길동', 'hong@example.com', '010-1234-5678', 1, '사장', '재직중', CURRENT_DATE, CURRENT_TIMESTAMP),
('qwe456', '김영희', 'kim@example.com', '010-9876-5432', 2, '대리', '재직중', CURRENT_DATE, CURRENT_TIMESTAMP);

INSERT INTO USERROLE (ROLENAME, USERID)
VALUES 
('ADMIN', 1),
('USER', 2);

INSERT INTO USERTODO (CONTENT, USERID, COMPLETED)
VALUES 
('로그인 1. 이메일로 로그인하게 바꾸기', 1, FALSE),
('홈 1. 공지사항 가장 최신거 불러오기', 1, TRUE),
('고객 이메일 회신', 1, FALSE),
('팀 주간 회의록 정리', 2, TRUE),
('신규 프로젝트 자료 조사', 1, FALSE);

INSERT INTO USERSCHEDULE (TITLE, CONTENT, STARTDATE, ENDDATE, USERID)
VALUES 
('팀 미팅', '프로젝트 상태 공유', '2025-06-09', '2025-06-12', 1),
('교육 일정', '신입 교육', '2025-06-03 14:00:00', '2025-06-03 16:00:00', 2);

INSERT INTO USEREMAIL (TITLE, CONTENT, SENDER, RECEIVER, IS_READ, SENT_AT)
VALUES 
('업무 관련 문의', '김영희 대리님, 보고서 관련하여 궁금한 점이 있습니다.', 1, 2, FALSE, CURRENT_TIMESTAMP),
('RE: 업무 관련 문의', '홍길동 사원님, 아래 내용 참고 바랍니다.', 2, 1, FALSE, CURRENT_TIMESTAMP),
('회의 자료 전달', '회의 자료를 첨부드렸습니다. 검토 부탁드립니다.', 1, 2, TRUE, CURRENT_TIMESTAMP);

-- 공지사항
INSERT INTO BOARD (TITLE, CONTENT, WRITER, ISNOTICE)
VALUES 
('시스템 점검 안내', '6월 10일(월) 00:00~04:00 시스템 점검 예정입니다.', '관리자', TRUE),
('신규 기능 업데이트 안내', '금일 신규 게시판 기능이 추가되었습니다.', '관리자', TRUE);
INSERT INTO BOARD (TITLE, CONTENT, WRITER, ISNOTICE)
VALUES (
  '2025 사용자 만족도 조사 실시 안내',
  '<p>안녕하세요, <strong>WeABiz 운영팀</strong>입니다.</p>

   <p>WeABiz는 항상 여러분의 의견을 소중하게 생각합니다.<br>
   <span style="color: #dc3545;"><strong>2025년 연말 사용자 만족도 조사</strong></span>를 아래와 같이 진행하오니 많은 참여 부탁드립니다.</p>

   <ul>
     <li><strong>조사 기간:</strong> 2025년 12월 1일 ~ 12월 15일</li>
     <li><strong>참여 대상:</strong> WeABiz 전체 사용자</li>
     <li><strong>참여 방법:</strong> <a href="/survey/2025" style="color: #007bff;">설문 페이지 바로가기</a></li>
   </ul>

   <p>설문에 참여해주신 분들께는 추첨을 통해 <strong>스타벅스 기프티콘</strong>을 드립니다! ☕</p>

   <p>여러분의 소중한 피드백은 더 나은 서비스로 보답하겠습니다.</p>

   <p style="text-align: right;">- WeABiz 고객지원팀</p>',
  '관리자',
  TRUE
);

-- 일반 게시판
INSERT INTO BOARD (TITLE, CONTENT, WRITER, ISNOTICE)
VALUES 
('점심 메뉴 추천 받아요', '오늘 점심 뭐 먹을지 고민되네요. 추천 좀요~', '홍길동', FALSE),
('이번 주 회식 일정 언제인가요?', '혹시 이번 주 회식 일정 아시는 분 있나요?', '김영희', FALSE);

INSERT INTO SCHEDULE (TITLE, CONTENT, STARTDATE, ENDDATE)
VALUES 
('전체 회의', '모든 부서 대상 주간 전체 회의', '2025-06-12 10:00:00', '2025-06-12 11:00:00'),
('보안 점검', '서버실 보안 점검 예정', '2025-06-10 09:00:00', '2025-06-10 10:30:00'),
('워크숍', '팀워크 증진 워크숍 진행', '2025-06-22 13:00:00', '2025-06-22 17:00:00');
