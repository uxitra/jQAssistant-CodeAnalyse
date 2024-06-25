package com.example.moviescheduler.schedule.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "movie_id", nullable = false)
    private String movieId;

    @Column(name = "theater_id", nullable = false)
    private String theaterId;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;
}
