package com.example.exampleproject.schedule.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {
}
