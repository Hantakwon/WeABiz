package com.labscale.weabiz.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labscale.weabiz.dept.entities.Department;
import com.labscale.weabiz.dept.service.DepartmentService;
import com.labscale.weabiz.user.entities.User;
import com.labscale.weabiz.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final DepartmentService departmentService;

	// 사용자 전체 목록
	@GetMapping
	public String userList(Model model) {
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		return "userList"; // → /WEB-INF/views/userList.jsp
	}

	// 특정 사용자 상세 정보
	@GetMapping("/{id}")
	public String userDetail(@PathVariable("id") Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "userDetail";
	}

	// 사용자 등록 폼
	@GetMapping("/insert")
	public String createForm(Model model) {
		List<Department> deptList = departmentService.findAll();
		model.addAttribute("deptList", deptList);
		model.addAttribute("user", new User());
		return "userInsert";
	}

	// 사용자 등록 처리
	@PostMapping("/insert")
	public String createUser(@ModelAttribute User user) {
		userService.save(user);
		return "redirect:/user";
	}

	// 사용자 수정 폼
	@GetMapping("/update/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		User user = userService.findById(id);
		List<Department> deptList = departmentService.findAll(); // 부서 목록 추가

		model.addAttribute("user", user);
		model.addAttribute("deptList", deptList); // 모델에 추가
		return "userUpdate";
	}

	// 사용자 수정 처리
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user, HttpSession session) {
		user.setUserId(id);
		userService.save(user);
		session.setAttribute("user", user); // update 후 재저장

		return "redirect:/user";
	}

	// 사용자 삭제
	@PostMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		userService.deleteById(id);
		return "redirect:/user";
	}
}