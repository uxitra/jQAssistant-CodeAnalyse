package com.example.exampleproject.theater.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<TheaterEntity, String> {
}
