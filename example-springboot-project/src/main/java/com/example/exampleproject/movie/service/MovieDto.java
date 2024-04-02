package com.example.exampleproject.movie.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private String id;
    private String title;
    private long durationInMinutes;
}

