package com.example.exampleproject.schedule.web;

import com.example.exampleproject.schedule.service.ScheduleDto;
import com.example.exampleproject.schedule.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @MockBean
    private ScheduleService scheduleService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetSchedulesCalledThenReturnSchedules() throws Exception {
        final var schedules = List.of(new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now()));
        when(scheduleService.getSchedules()).thenReturn(schedules);

        mockMvc.perform(get("/schedules"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(schedules)));
    }

    @Test
    void whenGetScheduleCalledWithIdThenReturnSchedule() throws Exception {
        final var schedule = new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now());
        when(scheduleService.getSchedule("scheduleId")).thenReturn(Optional.of(schedule));

        mockMvc.perform(get("/schedules/scheduleId"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(schedule)));
    }

    @Test
    void whenGetScheduleCalledWithIdThenReturnNotFound() throws Exception {
        when(scheduleService.getSchedule("scheduleId")).thenReturn(Optional.empty());

        mockMvc.perform(get("/schedules/scheduleId")).andExpect(status().isNotFound());
    }

    @Test
    void whenCreateScheduleCalledThenReturnSchedule() throws Exception {
        final var scheduleToCreate = new ScheduleDto(null, "movieId", "theaterId", Instant.now());
        final var createdSchedule = new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now());
        when(scheduleService.createSchedule(scheduleToCreate)).thenReturn(createdSchedule);

        mockMvc.perform(post("/schedules").content(objectMapper.writeValueAsString(scheduleToCreate))
                                          .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(createdSchedule)));
    }

    @Test
    void whenUpdateScheduleCalledThenReturnSchedule() throws Exception {
        final var scheduleToUpdate = new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now());
        final var updatedSchedule = new ScheduleDto("scheduleId", "movieId", "theaterId", Instant.now());
        when(scheduleService.updateSchedule("scheduleId", scheduleToUpdate)).thenReturn(updatedSchedule);

        mockMvc.perform(put("/schedules/scheduleId").content(objectMapper.writeValueAsString(scheduleToUpdate))
                                                    .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(updatedSchedule)));
    }

    @Test
    void whenDeleteScheduleCalledThenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/schedules/scheduleId")).andExpect(status().isNoContent());

        verify(scheduleService).deleteSchedule("scheduleId");
    }
}