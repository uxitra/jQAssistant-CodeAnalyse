package com.example.moviescheduler.theater.service;

import com.example.moviescheduler.theater.persistence.TheaterEntity;
import com.example.moviescheduler.theater.persistence.TheaterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TheaterServiceTest {

    @Mock
    private TheaterRepository theaterRepository;

    @Mock
    private TheaterMapper theaterMapper;

    @InjectMocks
    private TheaterService underTest;

    @Test
    void whenGetTheatersCalledThenGetTheatersFromRepositoryAndReturnDtos() {
        final var theaterEntity = mock(TheaterEntity.class);
        when(theaterRepository.findAll()).thenReturn(List.of(theaterEntity));

        final var mock = mock(TheaterDto.class);
        when(theaterMapper.toDto(theaterEntity)).thenReturn(mock);

        final var result = underTest.getTheaters();

        assertThat(result).containsExactly(mock);
    }

    @Test
    void whenGetTheaterCalledWithIdThenGetTheaterFromRepositoryAndReturnDto() {
        final var theaterEntity = mock(TheaterEntity.class);
        when(theaterRepository.findById("theaterId")).thenReturn(Optional.of(theaterEntity));

        final var mock = mock(TheaterDto.class);
        when(theaterMapper.toDto(theaterEntity)).thenReturn(mock);

        final var result = underTest.getTheater("theaterId");

        assertThat(result).contains(mock);
    }

    @Test
    void whenGetTheaterCalledWithIdThenGetTheaterFromRepositoryAndReturnEmpty() {
        when(theaterRepository.findById("theaterId")).thenReturn(Optional.empty());

        final var result = underTest.getTheater("theaterId");

        assertThat(result).isEmpty();
    }

    @Test
    void whenCreateTheaterCalledWithDtoThenSaveEntityAndReturnDto() {
        final var theaterDto = new TheaterDto(null, "name", 123);
        final var theaterEntity = mock(TheaterEntity.class);
        when(theaterMapper.toEntity(theaterDto)).thenReturn(theaterEntity);
        when(theaterRepository.save(theaterEntity)).thenReturn(theaterEntity);

        final var resultTheaterDto = mock(TheaterDto.class);
        when(theaterMapper.toDto(theaterEntity)).thenReturn(resultTheaterDto);

        final var result = underTest.createTheater(theaterDto);

        assertThat(result).isEqualTo(resultTheaterDto);
    }

    @Test
    void whenCreateTheaterCalledWithDtoWithIdThenThrowIllegalArgumentException() {
        final var theaterDto = new TheaterDto("theaterId", "name", 123);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.createTheater(theaterDto))
                                                                 .withMessage("Id must be null");
    }

    @Test
    void whenUpdateTheaterCalledWithIdAndDtoThenUpdateEntityAndReturnDto() {
        final var theaterDto = new TheaterDto("theaterId", "name", 123);
        final var theaterEntity = mock(TheaterEntity.class);
        when(theaterRepository.findById("theaterId")).thenReturn(Optional.of(theaterEntity));

        final var resultTheaterDto = mock(TheaterDto.class);
        when(theaterMapper.toDto(theaterEntity)).thenReturn(resultTheaterDto);

        final var result = underTest.updateTheater("theaterId", theaterDto);

        assertThat(result).isEqualTo(resultTheaterDto);
    }

    @Test
    void whenUpdateTheaterCalledWithIdAndDtoWithDifferentIdThenThrowIllegalArgumentException() {
        final var theaterDto = new TheaterDto("otherId", "name", 123);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.updateTheater("theaterId",
                                                                                                           theaterDto))
                                                                 .withMessage("Id must be null or match the path");
    }

    @Test
    void whenUpdateTheaterCalledWithIdAndDtoThenThrowIllegalArgumentException() {
        final var theaterDto = new TheaterDto("theaterId", "name", 123);
        when(theaterRepository.findById("theaterId")).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.updateTheater("theaterId",
                                                                                                           theaterDto))
                                                                 .withMessage("Theater with id theaterId not found");
    }

    @Test
    void whenDeleteTheaterCalledWithIdThenDeleteTheaterFromRepository() {
        underTest.deleteTheater("theaterId");

        verify(theaterRepository).deleteById("theaterId");
    }

    @Test
    void whenTheaterExistsCalledWithIdThenCallExistsByIdOnRepository() {
        when(theaterRepository.existsById("theaterId")).thenReturn(true);

        final var result = underTest.isTheaterExisting("theaterId");

        assertThat(result).isTrue();
    }
}