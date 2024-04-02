package com.example.exampleproject.movie.web;

import com.example.exampleproject.movie.service.MovieDto;
import com.example.exampleproject.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoviesController.class)
class MoviesControllerTest {
    @MockBean
    private MovieService movieService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetMoviesCalledThenReturnMovies() throws Exception {
        final var movies = List.of(new MovieDto("movieId", "title", 90));
        when(movieService.getMovies()).thenReturn(movies);

        objectMapper = new ObjectMapper();
        mockMvc.perform(get("/movies"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(movies)));
    }

    @Test
    void whenGetMovieCalledWithIdThenReturnMovie() throws Exception {
        final var movie = new MovieDto("movieId", "title", 90);
        when(movieService.getMovie("movieId")).thenReturn(java.util.Optional.of(movie));

        mockMvc.perform(get("/movies/movieId"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(movie)));
    }

    @Test
    void whenGetMovieCalledWithIdThenReturnNotFound() throws Exception {
        when(movieService.getMovie("movieId")).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies/movieId")).andExpect(status().isNotFound());
    }

    @Test
    void whenCreateMovieCalledThenReturnMovie() throws Exception {
        final var movieToCreate = new MovieDto(null, "movieToCreate", 90);
        final var createdMovie = new MovieDto("movieId", "createdMovie", 10);
        when(movieService.createMovie(movieToCreate)).thenReturn(createdMovie);

        mockMvc.perform(post("/movies").content(objectMapper.writeValueAsString(movieToCreate))
                                       .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(createdMovie)));
    }

    @Test
    void whenUpdateMovieCalledThenReturnMovie() throws Exception {
        final var movieToUpdate = new MovieDto("movieId", "movieToUpdate", 90);
        final var updatedMovie = new MovieDto("movieId", "updatedMovie", 10);
        when(movieService.updateMovie("movieId", movieToUpdate)).thenReturn(updatedMovie);

        mockMvc.perform(put("/movies/movieId").content(objectMapper.writeValueAsString(movieToUpdate))
                                              .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(updatedMovie)));
    }

    @Test
    void whenDeleteMovieCalledThenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/movies/movieId")).andExpect(status().isNoContent());

        verify(movieService).deleteMovie("movieId");
    }
}