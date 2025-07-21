package com.labscale.weabiz.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.labscale.weabiz.user.entities.UserEmail;
import com.labscale.weabiz.user.repository.UserEmailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEmailServiceImpl implements UserEmailService {

	private final UserEmailRepository userEmailRepository;
	
    @Override
    public UserEmail sendEmail(UserEmail email) {
        return userEmailRepository.save(email);
    }

    @Override
    public List<UserEmail> getInboxByUserId(Long userId) {
        return userEmailRepository.findByReceiver_UserId(userId);
    }

    @Override
    public List<UserEmail> getSentEmailsByUserId(Long userId) {
        return userEmailRepository.findBySender_UserId(userId);
    }

    @Override
    public UserEmail getEmailById(int emailId) {
        return userEmailRepository.findById(emailId).orElse(null);
    }

    @Override
    public void markAsRead(int emailId) {
        UserEmail email = userEmailRepository.findById(emailId).orElse(null);
        if (email != null && !email.isRead()) {
            email.setRead(true);
            userEmailRepository.save(email);
        }
    }

    @Override
    public void deleteEmail(int emailId) {
    	userEmailRepository.deleteById(emailId);
    }

}
