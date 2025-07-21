package com.labscale.weabiz.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labscale.weabiz.user.entities.UserEmail;

public interface UserEmailRepository extends JpaRepository<UserEmail, Integer> {
    List<UserEmail> findByReceiver_UserId(Long userId);

    List<UserEmail> findBySender_UserId(Long userId);
}
