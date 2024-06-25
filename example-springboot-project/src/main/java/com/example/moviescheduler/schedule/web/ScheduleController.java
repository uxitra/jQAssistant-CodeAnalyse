package com.example.moviescheduler.schedule.web;

import com.example.moviescheduler.schedule.service.ScheduleDto;
import com.example.moviescheduler.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public List<ScheduleDto> getSchedules() {
        return scheduleService.getSchedules();
    }

    @GetMapping("/{id}")
    public ScheduleDto getSchedule(@PathVariable(name = "id") final String id) {
        return scheduleService.getSchedule(id)
                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                             "Schedule not found"));
    }

    @PostMapping
    public ScheduleDto createSchedule(@RequestBody final ScheduleDto scheduleDto) {
        return scheduleService.createSchedule(scheduleDto);
    }

    @PutMapping("/{id}")
    public ScheduleDto updateSchedule(@PathVariable(name = "id") final String id,
                                      @RequestBody final ScheduleDto scheduleDto) {
        return scheduleService.updateSchedule(id, scheduleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable(name = "id") final String id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
