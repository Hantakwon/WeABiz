package com.labscale.weabiz.user.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.labscale.weabiz.user.entities.User;
import com.labscale.weabiz.user.entities.UserSchedule;
import com.labscale.weabiz.user.service.UserScheduleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/userSchedule")
@RequiredArgsConstructor
public class UserScheduleController {

	private final UserScheduleService scheduleService;

	// 📄 메인 화면 렌더링
	@GetMapping
	public String userSchedule(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		List<UserSchedule> schedules = scheduleService.getSchedulesByUser_UserId(user.getUserId());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		List<Map<String, Object>> scheduleList = schedules.stream().map(schedule -> {
			Map<String, Object> map = new HashMap<>();
			map.put("id", schedule.getScheduleId());
			map.put("title", schedule.getTitle());
			map.put("content", schedule.getContent());
			map.put("startdate", schedule.getStartdate().format(formatter)); // LocalDate → String
			map.put("enddate", schedule.getEnddate().format(formatter)); // LocalDate → String
			return map;
		}).collect(Collectors.toList());

		model.addAttribute("scheduleList", scheduleList);
		return "userSchedule";
	}

	@PostMapping("/insert")
	public String insertSchedule(HttpServletRequest request, HttpSession session) {
	    User user = (User) session.getAttribute("user");

	    // HTML에서 입력된 파라미터 수동 추출
	    String title = request.getParameter("title");
	    String content = request.getParameter("content");
	    String startDateStr = request.getParameter("startdate");
	    String endDateStr = request.getParameter("enddate");

	    // 문자열을 LocalDateTime으로 수동 변환 (시간은 00:00 고정)
	    LocalDateTime startDateTime = LocalDate.parse(startDateStr).atStartOfDay();
	    LocalDateTime endDateTime = LocalDate.parse(endDateStr).atStartOfDay();

	    // Schedule 객체에 수동으로 값 설정
	    UserSchedule schedule = new UserSchedule();
	    schedule.setTitle(title);
	    schedule.setContent(content);
	    schedule.setStartdate(startDateTime);
	    schedule.setEnddate(endDateTime);
	    schedule.setUser(user);

	    scheduleService.addSchedule(schedule);
	    return "redirect:/userSchedule";
	}

	// 일정 전체 수정
	@PostMapping("/update/{id}")
	public String updateSchedule(@PathVariable("id") int id, @RequestParam String title, @RequestParam String content,
			@RequestParam String startdate, @RequestParam String enddate) {

		UserSchedule userSchedule = scheduleService.findById(id);
		if (userSchedule != null) {
			userSchedule.setTitle(title);
			userSchedule.setContent(content);
			userSchedule.setStartdate(LocalDateTime.parse(startdate + "T00:00:00"));
			userSchedule.setEnddate(LocalDateTime.parse(enddate + "T00:00:00"));
			scheduleService.saveSchedule(userSchedule);
		}

		return "redirect:/userSchedule";
	}

	// 일정 삭제
	@PostMapping("/delete/{id}")
	public String deleteSchedule(@PathVariable("id") int id) {
		scheduleService.deleteSchedule(id);
		return "redirect:/userSchedule";
	}

	// 📅 전체 일정 조회 (FullCalendar 표시용)
	@GetMapping("/events")
	@ResponseBody
	public List<Map<String, Object>> getUserSchedules(@SessionAttribute("user") User user) {
		return scheduleService.getSchedulesByUser_UserId(user.getUserId()).stream().map(s -> {
			Map<String, Object> map = new HashMap<>();
			map.put("id", s.getScheduleId());
			map.put("title", s.getTitle());
			map.put("start", s.getStartdate().toString());
			map.put("end", s.getEnddate().toString());
			map.put("content", s.getContent());
			return map;
		}).collect(Collectors.toList());
	}

}
