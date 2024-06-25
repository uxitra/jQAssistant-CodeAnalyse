package com.example.moviescheduler.theater.service;

import com.example.moviescheduler.theater.persistence.TheaterEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TheaterMapperTest {

    private final TheaterMapper underTest = new TheaterMapper();

    @Test
    void whenToEntityAndToDtoCalledThenReturnTheSameObject() {
        final var originalDto = new TheaterDto("id", "name", 100);

        final var convertedEntity = underTest.toEntity(originalDto);
        final var convertedDto = underTest.toDto(convertedEntity);

        assertThat(originalDto).isEqualTo(convertedDto);
    }

    @Test
    void whenUpdateEntityCalledThenUpdateEntity() {
        final var entity = new TheaterEntity("id", "name", 100);
        final var dto = new TheaterDto("id", "newName", 200);

        underTest.updateEntity(entity, dto);

        assertThat(entity.getName()).isEqualTo("newName");
        assertThat(entity.getNumberOfSeats()).isEqualTo(200);
    }
}