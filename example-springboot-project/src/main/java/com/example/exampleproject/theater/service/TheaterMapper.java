package com.example.exampleproject.theater.service;

import com.example.exampleproject.theater.persistence.TheaterEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TheaterMapper {

    TheaterDto toDto(@NonNull final TheaterEntity theaterEntity) {
        return new TheaterDto(theaterEntity.getId(), theaterEntity.getName(), theaterEntity.getNumberOfSeats());
    }

    TheaterEntity toEntity(@NonNull final TheaterDto theaterDto) {
        return new TheaterEntity(theaterDto.getName(), theaterDto.getNumber(), theaterDto.getNumberOfSeats());
    }

    void updateEntity(@NonNull final TheaterEntity theaterEntity, @NonNull final TheaterDto theaterDto) {
        theaterEntity.setName(theaterDto.getNumber());
        theaterEntity.setNumberOfSeats(theaterDto.getNumberOfSeats());
    }
}
