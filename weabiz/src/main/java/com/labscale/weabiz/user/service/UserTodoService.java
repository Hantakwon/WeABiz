package com.labscale.weabiz.user.service;

import java.util.List;

import com.labscale.weabiz.user.entities.UserTodo;

public interface UserTodoService {
    List<UserTodo> getTodosByUser_UserId(Long userId);
    UserTodo findById(int todoId);
    UserTodo saveTodo(UserTodo todo);
    void deleteTodo(int todoId);
}
