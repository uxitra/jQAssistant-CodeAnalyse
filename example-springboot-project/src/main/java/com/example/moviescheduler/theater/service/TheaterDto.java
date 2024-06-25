package com.example.moviescheduler.theater.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheaterDto {
    private String id;
    private String name;
    private long numberOfSeats;
}
