package com.example.moviescheduler.theater.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<TheaterEntity, String> {
}
