package com.labscale.weabiz.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labscale.weabiz.user.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	List<UserRole> findByUser_UserId(Long userId);
}
