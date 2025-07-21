package com.labscale.weabiz.user.service;

import java.util.List;

import com.labscale.weabiz.user.entities.UserEmail;

public interface UserEmailService {
	
	UserEmail sendEmail(UserEmail email);
	List<UserEmail> getInboxByUserId(Long userId);
	List<UserEmail> getSentEmailsByUserId(Long userId);
	UserEmail getEmailById(int emailId);
	// 읽음 처리
	void markAsRead(int emailId);
	// 삭제
	void deleteEmail(int emailId);
}
