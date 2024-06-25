package com.example.moviescheduler.schedule.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {
}
