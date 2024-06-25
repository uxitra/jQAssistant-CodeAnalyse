package com.example.moviescheduler.schedule.service;

import com.example.moviescheduler.schedule.persistence.ScheduleEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleMapperTest {

    private final ScheduleMapper underTest = new ScheduleMapper();

    @Test
    void whenToEntityAndToDtoCalledThenReturnTheSameObject() {
        final var now = Instant.now();
        final var originalDto = new ScheduleDto("id", "movieId", "theaterId", now);

        final var convertedEntity = underTest.toEntity(originalDto);
        final var convertedDto = underTest.toDto(convertedEntity);

        assertThat(originalDto).isEqualTo(convertedDto);
    }

    @Test
    void whenUpdateEntityCalledThenUpdateEntity() {
        final var entity = new ScheduleEntity("id", "movieId", "theaterId", Instant.now());
        final var tomorrow = Instant.now().plus(1, ChronoUnit.DAYS);
        final var dto = new ScheduleDto("id", "newMovieId", "newTheaterId", tomorrow);

        underTest.updateEntity(entity, dto);

        assertThat(entity.getMovieId()).isEqualTo("newMovieId");
        assertThat(entity.getTheaterId()).isEqualTo("newTheaterId");
        assertThat(entity.getStartTime()).isEqualTo(tomorrow);
    }
}