<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>음성 파일 요약</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/aiMeeting.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class='container'>
		<h2>🧠 AI 회의록 자동 생성</h2>

		<div class="upload-box">
			<p>녹음 파일(.mp3/.wav)을 업로드하세요</p>
			<label class="upload-btn"> 파일 선택 <input type="file"
				id="audioFile" accept="audio/*">
			</label>
		</div>

		<div class="loading" id="loading">
			<span>.</span><span>.</span><span>.</span>
		</div>

		<div class="section">
			<h3>📜 인식된 텍스트</h3>
			<pre id="transcript">-</pre>
		</div>

		<div class="section">
			<h3>📝 요약 결과</h3>
			<pre id="summary">-</pre>
		</div>
	</div>

	<script>
		async function uploadAudio() {
			const fileInput = document.getElementById("audioFile");
			const file = fileInput.files[0];
			if (!file) {
				alert("음성 파일을 선택하세요.");
				return;
			}

			const formData = new FormData();
			formData.append("file", file);

			document.getElementById("loading").style.display = "block";

			try {
				const response = await
				fetch("http://localhost:8000/upload_audio", {
					method : "POST",
					body : formData
				});

				document.getElementById("loading").style.display = "none";

				if (!response.ok) {
					throw new Error("업로드 실패");
				}

				const result = await
				response.json();
				document.getElementById("transcript").textContent = result.transcript
						|| "텍스트 없음";
				document.getElementById("summary").textContent = result.summary
						|| "요약 없음";
			} catch (error) {
				document.getElementById("loading").style.display = "none";
				alert("오류 발생: " + error.message);
			}
		}

		document.getElementById("audioFile").addEventListener("change",
				uploadAudio);
	</script>
</body>
</html>
