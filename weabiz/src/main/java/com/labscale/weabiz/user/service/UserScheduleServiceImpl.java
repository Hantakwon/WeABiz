package com.labscale.weabiz.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.labscale.weabiz.user.entities.UserSchedule;
import com.labscale.weabiz.user.repository.UserScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserScheduleServiceImpl implements UserScheduleService {

    private final UserScheduleRepository scheduleRepository;

    @Override
    public List<UserSchedule> getSchedulesByUser_UserId(Long userId) {
        return scheduleRepository.findByUser_UserId(userId);
    }

    @Override
    public UserSchedule addSchedule(UserSchedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public UserSchedule saveSchedule(UserSchedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(int scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

	@Override
	public UserSchedule findById(int scheduleId) {
		return scheduleRepository.findById(scheduleId).get();
	}

    @Override
    public void updateAll(List<Integer> ids, List<String> titles, List<String> contents, List<String> startDates, List<String> endDates) {
        for (int i = 0; i < ids.size(); i++) {
            UserSchedule schedule = scheduleRepository.findById(ids.get(i)).orElse(null);
            if (schedule != null) {
                schedule.setTitle(titles.get(i));
                schedule.setContent(contents.get(i));
                schedule.setStartdate(LocalDateTime.parse(startDates.get(i) + "T00:00:00"));
                schedule.setEnddate(LocalDateTime.parse(endDates.get(i) + "T00:00:00"));
                scheduleRepository.save(schedule);
            }
        }
    }
}
