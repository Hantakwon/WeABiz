package com.labscale.weabiz.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.labscale.weabiz.entities.Schedule;
import com.labscale.weabiz.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule findById(Integer id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteById(Integer id) {
        scheduleRepository.deleteById(id);
    }
}
