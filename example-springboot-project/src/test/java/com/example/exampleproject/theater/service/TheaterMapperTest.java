package com.example.exampleproject.theater.service;

import com.example.exampleproject.theater.persistence.TheaterEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TheaterMapperTest {

    private final TheaterMapper underTest = new TheaterMapper();

    @Test
    void whenToEntityAndToDtoCalledThenReturnTheSameObject() {
        final var originalDto = new TheaterDto("id", "number", 100);

        final var convertedEntity = underTest.toEntity(originalDto);
        final var convertedDto = underTest.toDto(convertedEntity);

        assertThat(originalDto).isEqualTo(convertedDto);
    }

    @Test
    void whenUpdateEntityCalledThenUpdateEntity() {
        final var entity = new TheaterEntity("id", "number", 100);
        final var dtop = new TheaterDto("id", "newNumber", 200);

        underTest.updateEntity(entity, dtop);

        assertThat(entity.getName()).isEqualTo("newNumber");
        assertThat(entity.getNumberOfSeats()).isEqualTo(200);
    }
}