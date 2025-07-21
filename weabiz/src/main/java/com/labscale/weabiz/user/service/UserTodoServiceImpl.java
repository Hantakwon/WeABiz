package com.labscale.weabiz.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.labscale.weabiz.user.entities.UserTodo;
import com.labscale.weabiz.user.repository.UserTodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTodoServiceImpl implements UserTodoService {

	private final UserTodoRepository userTodoRepository;
	
	@Override
	public List<UserTodo> getTodosByUser_UserId(Long userId) {
		return userTodoRepository.findByUser_UserId(userId);
	}

	@Override
	public UserTodo saveTodo(UserTodo userTodo) {
		return userTodoRepository.save(userTodo);
	}

	@Override
	public void deleteTodo(int todoId) {
		userTodoRepository.deleteById(todoId);
	}

	@Override
	public UserTodo findById(int todoId) {
		return userTodoRepository.findById(todoId).get();
	}

}
