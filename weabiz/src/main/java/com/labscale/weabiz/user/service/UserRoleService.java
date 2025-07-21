package com.labscale.weabiz.user.service;

import java.util.List;

import com.labscale.weabiz.user.entities.UserRole;

public interface UserRoleService {
	List<UserRole> getUserRoleByUserId(Long userId);
}