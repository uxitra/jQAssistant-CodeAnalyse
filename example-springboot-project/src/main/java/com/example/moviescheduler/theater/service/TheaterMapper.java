package com.example.moviescheduler.theater.service;

import com.example.moviescheduler.theater.persistence.TheaterEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TheaterMapper {

    TheaterDto toDto(@NonNull final TheaterEntity theaterEntity) {
        return new TheaterDto(theaterEntity.getId(), theaterEntity.getName(), theaterEntity.getNumberOfSeats());
    }

    TheaterEntity toEntity(@NonNull final TheaterDto theaterDto) {
        return new TheaterEntity(theaterDto.getId(), theaterDto.getName(), theaterDto.getNumberOfSeats());
    }

    void updateEntity(@NonNull final TheaterEntity theaterEntity, @NonNull final TheaterDto theaterDto) {
        theaterEntity.setName(theaterDto.getName());
        theaterEntity.setNumberOfSeats(theaterDto.getNumberOfSeats());
    }
}
