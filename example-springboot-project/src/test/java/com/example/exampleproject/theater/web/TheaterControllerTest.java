package com.example.exampleproject.theater.web;

import com.example.exampleproject.theater.service.TheaterDto;
import com.example.exampleproject.theater.service.TheaterService;
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

@WebMvcTest(TheaterController.class)
class TheaterControllerTest {
    @MockBean
    private TheaterService theaterService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetTheatersCalledThenReturnTheaters() throws Exception {
        final var theaters = List.of(new TheaterDto("theaterId", "theaterName", 100));
        when(theaterService.getTheaters()).thenReturn(theaters);

        mockMvc.perform(get("/theaters"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(theaters)));
    }

    @Test
    void whenGetTheaterCalledWithIdThenReturnTheater() throws Exception {
        final var theater = new TheaterDto("theaterId", "theaterName", 100);
        when(theaterService.getTheater("theaterId")).thenReturn(Optional.of(theater));

        mockMvc.perform(get("/theaters/theaterId"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(theater)));
    }

    @Test
    void whenGetTheaterCalledWithIdThenReturnNotFound() throws Exception {
        when(theaterService.getTheater("theaterId")).thenReturn(Optional.empty());

        mockMvc.perform(get("/theaters/theaterId")).andExpect(status().isNotFound());
    }

    @Test
    void whenCreateTheaterCalledThenReturnTheater() throws Exception {
        final var theaterToCreate = new TheaterDto(null, "theaterName", 100);
        final var createdTheater = new TheaterDto("theaterId", "theaterName", 120);
        when(theaterService.createTheater(theaterToCreate)).thenReturn(createdTheater);

        mockMvc.perform(post("/theaters").content(objectMapper.writeValueAsString(theaterToCreate))
                                         .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(createdTheater)));
    }

    @Test
    void whenUpdateTheaterCalledThenReturnTheater() throws Exception {
        final var theaterToUpdate = new TheaterDto("theaterId", "theaterName", 100);
        final var updatedTheater = new TheaterDto("theaterId", "updatedTheaterName", 120);
        when(theaterService.updateTheater("theaterId", theaterToUpdate)).thenReturn(updatedTheater);

        mockMvc.perform(put("/theaters/theaterId").content(objectMapper.writeValueAsString(theaterToUpdate))
                                                  .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(updatedTheater)));
    }

    @Test
    void whenDeleteTheaterCalledThenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/theaters/theaterId")).andExpect(status().isNoContent());

        verify(theaterService).deleteTheater("theaterId");
    }
}