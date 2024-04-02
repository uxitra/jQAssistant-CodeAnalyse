package com.example.exampleproject.theater.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheaterDto {
    private String name;
    private String number;
    private long numberOfSeats;
}
