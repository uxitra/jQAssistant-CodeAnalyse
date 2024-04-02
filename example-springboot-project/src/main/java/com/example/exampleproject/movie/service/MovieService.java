package com.example.exampleproject.movie.service;

import com.example.exampleproject.movie.persistence.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService implements MovieExistenceChecker {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public List<MovieDto> getMovies() {
        return movieRepository.findAll().stream().map(movieMapper::toDto).toList();
    }

    public Optional<MovieDto> getMovie(@NonNull final String id) {
        return movieRepository.findById(id).map(movieMapper::toDto);
    }

    public MovieDto createMovie(@NonNull final MovieDto movieDto) {
        if (movieDto.getId() != null) {
            throw new IllegalArgumentException("Id must be null");
        }
        return movieMapper.toDto(movieRepository.save(movieMapper.toEntity(movieDto)));
    }

    public MovieDto updateMovie(@NonNull final String id, @NonNull final MovieDto movieDto) {
        if (movieDto.getId() != null && !movieDto.getId().equals(id)) {
            throw new IllegalArgumentException("Id must be null or match the path");
        }

        return movieRepository.findById(id).map(movieEntity -> {
            movieMapper.updateEntity(movieEntity, movieDto);
            movieRepository.save(movieEntity);
            return movieMapper.toDto(movieEntity);
        }).orElseThrow(() -> new IllegalArgumentException("Movie with id " + id + " not found"));
    }

    public void deleteMovie(@NonNull final String id) {
        movieRepository.deleteById(id);
    }

    @Override
    public boolean isMovieExisting(final String movieId) {
        return movieRepository.existsById(movieId);
    }
}
