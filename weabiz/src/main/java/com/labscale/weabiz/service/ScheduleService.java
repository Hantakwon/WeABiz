package com.labscale.weabiz.service;

import java.util.List;

import com.labscale.weabiz.entities.Schedule;

public interface ScheduleService {
    List<Schedule> findAll();
    Schedule findById(Integer id);
    void save(Schedule schedule);
    void deleteById(Integer id);
}
