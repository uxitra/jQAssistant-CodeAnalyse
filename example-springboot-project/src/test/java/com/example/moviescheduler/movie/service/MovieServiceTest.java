package com.example.moviescheduler.movie.service;

import com.example.moviescheduler.movie.persistence.MovieEntity;
import com.example.moviescheduler.movie.persistence.MovieRepository;
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
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;
    @InjectMocks
    private MovieService underTest;

    @Test
    void whenGetMoviesCalledThenGetMoviesFromRepositoryAndReturnDtos() {
        final var movieEntity = mock(MovieEntity.class);
        when(movieRepository.findAll()).thenReturn(List.of(movieEntity));

        final var mock = mock(MovieDto.class);
        when(movieMapper.toDto(movieEntity)).thenReturn(mock);

        final var result = underTest.getMovies();

        assertThat(result).containsExactly(mock);
    }

    @Test
    void whenGetMovieCalledWithIdThenGetMovieFromRepositoryAndReturnDto() {
        final var movieEntity = mock(MovieEntity.class);
        when(movieRepository.findById("movieId")).thenReturn(Optional.of(movieEntity));

        final var mock = mock(MovieDto.class);
        when(movieMapper.toDto(movieEntity)).thenReturn(mock);

        final var result = underTest.getMovie("movieId");

        assertThat(result).contains(mock);
    }

    @Test
    void whenGetMovieCalledWithIdThenGetMovieFromRepositoryAndReturnEmpty() {
        when(movieRepository.findById("movieId")).thenReturn(Optional.empty());

        final var result = underTest.getMovie("movieId");

        assertThat(result).isEmpty();
    }

    @Test
    void whenCreateMovieCalledWithDtoThenSaveEntityAndReturnDto() {
        final var movieDto = new MovieDto(null, "title", 123);
        final var movieEntity = mock(MovieEntity.class);
        when(movieMapper.toEntity(movieDto)).thenReturn(movieEntity);
        when(movieRepository.save(movieEntity)).thenReturn(movieEntity);

        final var resultMovieDto = mock(MovieDto.class);
        when(movieMapper.toDto(movieEntity)).thenReturn(resultMovieDto);

        final var result = underTest.createMovie(movieDto);

        assertThat(result).isEqualTo(resultMovieDto);
    }

    @Test
    void whenCreateMovieCalledWithDtoWithIdThenThrowIllegalArgumentException() {
        final var movieDto = new MovieDto("movieId", "title", 123);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.createMovie(movieDto))
                                                                 .withMessage("Id must be null");
    }

    @Test
    void whenUpdateMovieCalledWithIdAndDtoThenUpdateEntityAndReturnDto() {
        final var movieDto = new MovieDto("movieId", "title", 123);
        final var movieEntity = mock(MovieEntity.class);
        when(movieRepository.findById("movieId")).thenReturn(Optional.of(movieEntity));

        final var resultMovieDto = mock(MovieDto.class);
        when(movieMapper.toDto(movieEntity)).thenReturn(resultMovieDto);

        final var result = underTest.updateMovie("movieId", movieDto);

        assertThat(result).isEqualTo(resultMovieDto);
    }

    @Test
    void whenUpdateMovieCalledWithIdAndDtoWithDifferentIdThenThrowIllegalArgumentException() {
        final var movieDto = new MovieDto("otherId", "title", 123);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.updateMovie("movieId",
                                                                                                         movieDto))
                                                                 .withMessage("Id must be null or match the path");
    }

    @Test
    void whenUpdateMovieCalledWithIdAndDtoThenThrowIllegalArgumentException() {
        final var movieDto = new MovieDto("movieId", "title", 123);
        when(movieRepository.findById("movieId")).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.updateMovie("movieId",
                                                                                                         movieDto))
                                                                 .withMessage("Movie with id movieId not found");
    }

    @Test
    void whenDeleteMovieCalledWithIdThenDeleteMovieFromRepository() {
        underTest.deleteMovie("movieId");

        verify(movieRepository).deleteById("movieId");
    }

    @Test
    void whenIsMovieExistingCalledWithIdThenCallExistsByIdOnRepository() {
        when(movieRepository.existsById("movieId")).thenReturn(true);

        final var result = underTest.isMovieExisting("movieId");

        assertThat(result).isTrue();
    }
}