package com.labscale.weabiz.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.labscale.weabiz.user.entities.UserRole;
import com.labscale.weabiz.user.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

	private final UserRoleRepository userRoleRepository;

	@Override
	public List<UserRole> getUserRoleByUserId(Long userId) {
		return userRoleRepository.findByUser_UserId(userId);
	}

}
