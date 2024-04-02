package com.example.exampleproject.schedule.service;

import com.example.exampleproject.movie.service.MovieExistenceChecker;
import com.example.exampleproject.schedule.persistence.ScheduleEntity;
import com.example.exampleproject.schedule.persistence.ScheduleRepository;
import com.example.exampleproject.theater.service.TheaterExistenceChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ScheduleMapper scheduleMapper;
    @Mock
    private MovieExistenceChecker movieExistenceChecker;
    @Mock
    private TheaterExistenceChecker theaterExistenceChecker;
    @InjectMocks
    private ScheduleService underTest;

    @Test
    void whenGetSchedulesCalledThenGetSchedulesFromRepositoryAndReturnDtos() {
        final var scheduleEntity = mock(ScheduleEntity.class);
        when(scheduleRepository.findAll()).thenReturn(List.of(scheduleEntity));

        final var mock = mock(ScheduleDto.class);
        when(scheduleMapper.toDto(scheduleEntity)).thenReturn(mock);

        final var result = underTest.getSchedules();

        assertThat(result).containsExactly(mock);
    }

    @Test
    void whenGetScheduleCalledWithIdThenGetScheduleFromRepositoryAndReturnDto() {
        final var scheduleEntity = mock(ScheduleEntity.class);
        when(scheduleRepository.findById("scheduleId")).thenReturn(Optional.of(scheduleEntity));

        final var mock = mock(ScheduleDto.class);
        when(scheduleMapper.toDto(scheduleEntity)).thenReturn(mock);

        final var result = underTest.getSchedule("scheduleId");

        assertThat(result).contains(mock);
    }

    @Test
    void whenGetScheduleCalledWithIdThenGetScheduleFromRepositoryAndReturnEmpty() {
        when(scheduleRepository.findById("scheduleId")).thenReturn(Optional.empty());

        final var result = underTest.getSchedule("scheduleId");

        assertThat(result).isEmpty();
    }

    @Test
    void whenCreateScheduleCalledWithDtoContainingValidMovieAndTheaterThenSaveEntityAndReturnDto() {
        final var scheduleDto = new ScheduleDto(null, "movieId", "theaterId", Instant.now());
        final var scheduleEntity = mock(ScheduleEntity.class);
        when(scheduleMapper.toEntity(scheduleDto)).thenReturn(scheduleEntity);
        when(movieExistenceChecker.isMovieExisting("movieId")).thenReturn(true);
        when(theaterExistenceChecker.isTheaterExisting("theaterId")).thenReturn(true);
        when(scheduleRepository.save(scheduleEntity)).thenReturn(scheduleEntity);

        final var resultScheduleDto = mock(ScheduleDto.class);
        when(scheduleMapper.toDto(scheduleEntity)).thenReturn(resultScheduleDto);

        final var result = underTest.createSchedule(scheduleDto);

        assertThat(result).isEqualTo(resultScheduleDto);
    }

    @Test
    void whenCreateScheduleCalledWithDtoContainingInvalidMovieThenThrowException() {
        final var scheduleDto = new ScheduleDto(null, "movieId", "theaterId", Instant.now());
        when(movieExistenceChecker.isMovieExisting("movieId")).thenReturn(false);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.createSchedule(scheduleDto))
                                                                 .withMessage("Movie with id movieId does not exist")
                                                                 .withNoCause();
    }

    @Test
    void whenCreateScheduleCalledWithDtoContainingInvalidTheaterThenThrowException() {
        final var scheduleDto = new ScheduleDto(null, "movieId", "theaterId", Instant.now());
        when(movieExistenceChecker.isMovieExisting("movieId")).thenReturn(true);
        when(theaterExistenceChecker.isTheaterExisting("theaterId")).thenReturn(false);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.createSchedule(scheduleDto))
                                                                 .withMessage("Theater with id theaterId does not exist")
                                                                 .withNoCause();
    }

    @Test
    void whenCreateScheduleCalledWithDtoWithIdThenThrowIllegalArgumentException() {
        final var scheduleDto = new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.createSchedule(scheduleDto))
                                                                 .withMessage("Id must be null");
    }

    @Test
    void whenUpdateScheduleCalledWithIdAndDtoWithValidMovieAndTheaterThenUpdateEntityAndReturnDto() {
        final var scheduleDto = new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now());
        final var scheduleEntity = mock(ScheduleEntity.class);
        when(scheduleRepository.findById("scheduleId")).thenReturn(Optional.of(scheduleEntity));
        final var resultScheduleDto = mock(ScheduleDto.class);
        when(scheduleMapper.toDto(scheduleEntity)).thenReturn(resultScheduleDto);
        when(movieExistenceChecker.isMovieExisting("movieId")).thenReturn(true);
        when(theaterExistenceChecker.isTheaterExisting("theaterId")).thenReturn(true);

        final var result = underTest.updateSchedule("scheduleId", scheduleDto);

        assertThat(result).isEqualTo(resultScheduleDto);
    }

    @Test
    void whenUpdateScheduleCalledWithIdAndDtoWithInvalidMovieThenThrowIllegalArgumentException() {
        final var scheduleDto = new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now());
        when(scheduleRepository.findById("scheduleId")).thenReturn(Optional.of(mock(ScheduleEntity.class)));
        when(movieExistenceChecker.isMovieExisting("movieId")).thenReturn(false);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.updateSchedule("scheduleId",
                                                                                                            scheduleDto))
                                                                 .withMessage("Movie with id movieId does not exist");
    }

    @Test
    void whenUpdateScheduleCalledWithIdAndDtoWithInvalidTheaterThenThrowIllegalArgumentException() {
        final var scheduleDto = new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now());
        when(scheduleRepository.findById("scheduleId")).thenReturn(Optional.of(mock(ScheduleEntity.class)));
        when(movieExistenceChecker.isMovieExisting("movieId")).thenReturn(true);
        when(theaterExistenceChecker.isTheaterExisting("theaterId")).thenReturn(false);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.updateSchedule("scheduleId",
                                                                                                            scheduleDto))
                                                                 .withMessage("Theater with id theaterId does not exist");
    }

    @Test
    void whenUpdateScheduleCalledWithIdAndDtoWithDifferentIdThenThrowIllegalArgumentException() {
        final var scheduleDto = new ScheduleDto("otherId", "movieId", "theaterId", Instant.now());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.updateSchedule("scheduleId",
                                                                                                            scheduleDto))
                                                                 .withMessage("Id must be null or match the path");
    }

    @Test
    void whenUpdateScheduleCalledWithIdAndDtoThenThrowIllegalArgumentException() {
        final var scheduleDto = new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now());
        when(scheduleRepository.findById("scheduleId")).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.updateSchedule("scheduleId",
                                                                                                            scheduleDto))
                                                                 .withMessage("Schedule with id scheduleId not found");
    }

    @Test
    void whenDeleteScheduleCalledWithIdThenDeleteScheduleFromRepository() {
        underTest.deleteSchedule("scheduleId");

        verify(scheduleRepository).deleteById("scheduleId");
    }
}