package com.labscale.weabiz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

import com.labscale.weabiz.board.entities.Board;
import com.labscale.weabiz.board.service.BoardService;
import com.labscale.weabiz.dept.entities.Department;
import com.labscale.weabiz.dept.service.DepartmentService;
import com.labscale.weabiz.user.entities.User;
import com.labscale.weabiz.user.entities.UserRole;
import com.labscale.weabiz.user.entities.UserTodo;
import com.labscale.weabiz.user.service.UserRoleService;
import com.labscale.weabiz.user.service.UserScheduleService;
import com.labscale.weabiz.user.service.UserService;
import com.labscale.weabiz.user.service.UserTodoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final BoardService boardService;
	private final UserRoleService userRoleService;
	private final UserTodoService userTodoService;
	private final UserService userService;
	private final UserScheduleService scheduleService;
	private final DepartmentService departmentService;
	private final RestTemplate restTemplate;

	@GetMapping("/")
	public String MainPage() {
		return "Index";
	}

	@GetMapping("/home")
	public String homePage(HttpSession session, Model model) {

		Board latestNotice = boardService.findLatestNotice();

		User user = (User) session.getAttribute("user");
		List<UserTodo> todoList = userTodoService.getTodosByUser_UserId(user.getUserId());

		model.addAttribute("latestNotice", latestNotice);
		model.addAttribute("todoList", todoList);
		return "home";
	}

	@PostMapping("/login")
	public String userLogin(HttpSession session, @RequestParam("email") String email,
			@RequestParam("password") String password) {

		if (email == null || password == null || email.isBlank() || password.isBlank()) {
			return "redirect:/"; // 입력값이 비어있을 경우
		}

		User user = userService.findByEmail(email); // 이메일로 사용자 검색

		if (user != null && password.equals(user.getPassword())) { // 비밀번호 일치 확인
			List<UserTodo> todoList = userTodoService.getTodosByUser_UserId(user.getUserId());
			List<UserRole> userRole = userRoleService.getUserRoleByUserId(user.getUserId());

			String role = (userRole != null && !userRole.isEmpty()) ? userRole.get(0).getRoleName() : "USER";

			session.setAttribute("todoList", todoList);
			session.setAttribute("user", user);
			session.setAttribute("role", role);

			return "redirect:/home";
		}

		return "redirect:/"; // 로그인 실패 시
	}

	@GetMapping("/logout")
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/";
	}

	@GetMapping("/aiMeeting")
	public String meetingPage() {
		return "aiMeeting";
	}

	@GetMapping("/aiChat")
	public String aiChatPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Department dept = departmentService.findById(user.getDeptId());
		System.out.println(dept.getDeptId() + dept.getDeptName());
		model.addAttribute("deptName", dept.getDeptName()); // 예: "인사과"
		return "aiChat";
	}

	@PostMapping("/ask")
	@ResponseBody
	public ResponseEntity<Map<String, String>> ask(@RequestBody Map<String, Object> payload) {
		String question = (String) payload.get("question");
		String dept = (String) payload.get("dept");
		List<Map<String, String>> history = (List<Map<String, String>>) payload.get("history");

		// ✅ FastAPI에 보낼 JSON 객체
		Map<String, Object> fastApiRequest = new HashMap<>();
		fastApiRequest.put("question", question); // 반드시 포함
		fastApiRequest.put("dept", dept); // 반드시 포함
		fastApiRequest.put("history", history); // Optional

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(fastApiRequest, headers);

		String fastApiUrl = "http://localhost:8000/ask";
		ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, entity, Map.class);

		Map<String, String> result = new HashMap<>();
		result.put("answer", (String) response.getBody().get("answer"));
		return ResponseEntity.ok(result);
	}

}
