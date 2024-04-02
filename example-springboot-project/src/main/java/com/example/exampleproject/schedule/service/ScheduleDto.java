package com.example.exampleproject.schedule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private String id;
    private String movieId;
    private String theaterId;
    private Instant startTime;
}
