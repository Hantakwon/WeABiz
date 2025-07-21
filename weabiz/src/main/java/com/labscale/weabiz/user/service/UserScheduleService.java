package com.labscale.weabiz.user.service;

import java.time.LocalDateTime;
import java.util.List;

import com.labscale.weabiz.user.entities.UserSchedule;

public interface UserScheduleService {
    List<UserSchedule> getSchedulesByUser_UserId(Long userId);
    UserSchedule findById(int scheduleId);
    UserSchedule addSchedule(UserSchedule schedule);
    UserSchedule saveSchedule(UserSchedule schedule);
    void updateAll(List<Integer> ids, List<String> titles, List<String> contents, List<String> startDates, List<String> endDates);
    void deleteSchedule(int scheduleId);
}
