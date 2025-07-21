package com.labscale.weabiz.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.labscale.weabiz.user.entities.User;
import com.labscale.weabiz.user.entities.UserTodo;
import com.labscale.weabiz.user.service.UserTodoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/userTodoList")
@RequiredArgsConstructor
public class UserTodoController {

	private final UserTodoService userTodoService;

	@GetMapping
	public String userList(Model model, @SessionAttribute("user") User user) {
		List<UserTodo> userTodoList = userTodoService.getTodosByUser_UserId(user.getUserId());
		model.addAttribute("userTodoList", userTodoList);
		return "userTodoList"; // → /WEB-INF/views/userList.jsp
	}

	@PostMapping("/updateAll")
	public String updateAllTodos(@RequestParam(value = "todoIds", required = false) List<Integer> todoIds,
			@RequestParam(value = "contents", required = false) List<String> contents,
			@RequestParam(value = "newContents", required = false) List<String> newContents,
			@RequestParam(value = "deletedIds", required = false) List<Integer> deletedIds,
			@RequestParam(value = "completedValues", required = false) List<Integer> completedIds,
			@SessionAttribute("user") User user, HttpSession session) {

		Long userId = user.getUserId();

		// 삭제
		if (deletedIds != null) {
			for (int id : deletedIds) {
				userTodoService.deleteTodo(id);
			}
		}

		// 수정
		if (todoIds != null && contents != null) {
			List<UserTodo> todos = userTodoService.getTodosByUser_UserId(userId);
			for (int i = 0; i < todoIds.size(); i++) {
				final int todoId = todoIds.get(i);
				UserTodo todo = todos.stream().filter(t -> t.getTodoId() == todoId).findFirst().orElse(null);
				if (todo != null) {
					todo.setContent(contents.get(i));
					// completed 값 설정
					boolean isCompleted = completedIds != null && completedIds.contains(todoId);
					todo.setCompleted(isCompleted);
					userTodoService.saveTodo(todo);
				}
			}
		}

		// 추가
		if (newContents != null) {
			for (String content : newContents) {
				if (content != null && !content.trim().isEmpty()) {
					UserTodo todo = new UserTodo();
					todo.setContent(content);
					todo.setUser(user);
					todo.setCompleted(false); // 새 항목은 기본적으로 미완료
					userTodoService.saveTodo(todo);
				}
			}
		}

		List<UserTodo> updatedList = userTodoService.getTodosByUser_UserId(userId);
		session.setAttribute("todoList", updatedList);

		return "redirect:/userTodoList";
	}

	@PostMapping("/complete")
	public String completeTodo(@RequestParam("todoId") int todoId,
			@RequestParam(value = "completed", required = false) boolean completedParam) {
		UserTodo todo = userTodoService.findById(todoId);
		boolean completed = (completedParam);

		todo.setCompleted(completed);
		userTodoService.saveTodo(todo);

		return "redirect:/home";
	}

}