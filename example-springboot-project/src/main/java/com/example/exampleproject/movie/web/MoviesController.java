package com.example.exampleproject.movie.web;

import com.example.exampleproject.movie.service.MovieDto;
import com.example.exampleproject.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
class MoviesController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/{id}")
    public MovieDto getMovie(@PathVariable(name = "id") final String id) {
        return movieService.getMovie(id)
                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }

    @PostMapping
    public MovieDto createMovie(@RequestBody final MovieDto movieDto) {
        return movieService.createMovie(movieDto);
    }

    @PutMapping("/{id}")
    public MovieDto updateMovie(@PathVariable(name = "id") final String id, @RequestBody final MovieDto movieDto) {
        return movieService.updateMovie(id, movieDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable(name = "id") final String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
