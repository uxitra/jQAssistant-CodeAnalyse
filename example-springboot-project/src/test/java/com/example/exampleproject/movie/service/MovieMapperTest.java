package com.example.exampleproject.movie.service;

import com.example.exampleproject.movie.persistence.MovieEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MovieMapperTest {
    private final MovieMapper underTest = new MovieMapper();

    @Test
    void whenToEntityAndToDtoCalledThenReturnTheSameObject() {
        final var originalDto = new MovieDto("id", "title", 100);

        final var convertedEntity = underTest.toEntity(originalDto);
        final var convertedDto = underTest.toDto(convertedEntity);

        assertThat(originalDto).isEqualTo(convertedDto);
    }

    @Test
    void whenUpdateEntityCalledThenUpdateEntity() {
        final var entity = new MovieEntity("id", "title", 100);
        final var dto = new MovieDto("id", "newTitle", 200);

        underTest.updateEntity(entity, dto);

        assertThat(entity.getTitle()).isEqualTo("newTitle");
        assertThat(entity.getDurationInMinutes()).isEqualTo(200);
    }
}