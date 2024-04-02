package com.example.exampleproject.movie.service;

import com.example.exampleproject.movie.persistence.MovieEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class MovieMapper {

    MovieDto toDto(@NonNull final MovieEntity movieEntity) {
        return new MovieDto(movieEntity.getId(), movieEntity.getTitle(), movieEntity.getDurationInMinutes());
    }

    MovieEntity toEntity(@NonNull final MovieDto movieDto) {
        return new MovieEntity(movieDto.getId(), movieDto.getTitle(), movieDto.getDurationInMinutes());
    }

    void updateEntity(@NonNull final MovieEntity movieEntity, @NonNull final MovieDto movieDto) {
        movieEntity.setTitle(movieDto.getTitle());
        movieEntity.setDurationInMinutes(movieDto.getDurationInMinutes());
    }
}
