package com.labscale.weabiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labscale.weabiz.entities.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}

