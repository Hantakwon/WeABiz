package com.labscale.weabiz.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labscale.weabiz.user.entities.UserTodo;

public interface UserTodoRepository extends JpaRepository<UserTodo, Integer>{
	List<UserTodo> findByUser_UserId(Long userId);

}
