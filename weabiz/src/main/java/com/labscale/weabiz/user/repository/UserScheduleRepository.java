package com.labscale.weabiz.user.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.labscale.weabiz.user.entities.UserSchedule;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Integer> {
	List<UserSchedule> findByUser_UserId(Long userId);
}
