package com.labscale.weabiz.user.controller;

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

import com.labscale.weabiz.user.entities.User;
import com.labscale.weabiz.user.entities.UserEmail;
import com.labscale.weabiz.user.service.UserEmailService;
import com.labscale.weabiz.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/userEmail")
@RequiredArgsConstructor
public class UserEmailController {

	private final UserService userService;
	private final UserEmailService userEmailService;

	@GetMapping
	public String email(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

		List<UserEmail> emailList = userEmailService.getInboxByUserId(user.getUserId());

		List<Map<String, Object>> emails = emailList.stream().map(email -> {
			Map<String, Object> map = new HashMap<>();
			map.put("emailId", email.getEmailId());
			map.put("title", email.getTitle());
			map.put("sender", email.getSender());
			map.put("read", email.isRead());
			// LocalDateTime을 포맷해서 문자열로 넣기
			map.put("sentAt", email.getSentAt().format(formatter));
			return map;
		}).collect(Collectors.toList());

		model.addAttribute("emails", emails);
		return "userEmailList";
	}

	@GetMapping("/sent")
	public String sentEmails(HttpSession session, Model model) {
	    User user = (User) session.getAttribute("user");

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

	    // 보낸 메일 조회
	    List<UserEmail> emailList = userEmailService.getSentEmailsByUserId(user.getUserId());

	    List<Map<String, Object>> emails = emailList.stream().map(email -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("emailId", email.getEmailId());
	        map.put("title", email.getTitle());
	        map.put("receiver", email.getReceiver()); // 받는 사용자 객체
	        map.put("sentAt", email.getSentAt().format(formatter));
	        map.put("read", email.isRead()); // 수신자가 읽었는지 여부
	        return map;
	    }).collect(Collectors.toList());

	    model.addAttribute("sentEmails", emails);
	    return "userEmailSent"; // 보낸 메일 화면 JSP
	}


	@GetMapping("/{emailId}")
	public String viewEmail(@PathVariable int emailId, Model model) {
		UserEmail email = userEmailService.getEmailById(emailId);
		if (email == null) {
			return "redirect:/userEmail/inbox?error=notfound";
		}

		// 읽음 처리
		if (!email.isRead()) {
			userEmailService.markAsRead(emailId);
			email.setRead(true); // UI에 반영되도록
		}

		model.addAttribute("email", email);
		return "userEmailDetail";
	}

	@GetMapping("/send")
	public String sendEmail() {
		return "userEmailSend";
	}

	@PostMapping("/send")
	public String sendEmail(@RequestParam String receiverEmail,  // email로 받기
	                        @RequestParam String title,
	                        @RequestParam String content,
	                        HttpSession session) {

	    // 로그인 사용자 확인
	    User sender = (User) session.getAttribute("user");
	    if (sender == null) {
	        return "redirect:/login";
	    }

	    // 이메일로 수신자 조회
	    User receiver = userService.findByEmail(receiverEmail);
	    if (receiver == null) {
	        return "redirect:/userEmail/send?error=notfound";
	    }

	    // 메일 객체 생성
	    UserEmail email = new UserEmail();
	    email.setSender(sender);
	    email.setReceiver(receiver);
	    email.setTitle(title);
	    email.setContent(content);
	    email.setSentAt(LocalDateTime.now());
	    email.setRead(false);

	    // 저장
	    userEmailService.sendEmail(email);

	    return "redirect:/userEmail";
	}

}
