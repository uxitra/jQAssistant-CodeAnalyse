package com.example.moviescheduler.movie.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, String> {
}
