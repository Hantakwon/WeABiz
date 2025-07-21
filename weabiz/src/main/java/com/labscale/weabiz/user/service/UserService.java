package com.labscale.weabiz.user.service;

import java.util.List;

import com.labscale.weabiz.user.entities.User;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    void save(User user);
    void deleteById(Long id);
    User findByEmail(String email);
}
