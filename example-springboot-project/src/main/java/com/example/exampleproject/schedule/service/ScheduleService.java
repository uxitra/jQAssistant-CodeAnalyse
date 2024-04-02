package com.example.exampleproject.schedule.service;

import com.example.exampleproject.movie.service.MovieExistenceChecker;
import com.example.exampleproject.schedule.persistence.ScheduleRepository;
import com.example.exampleproject.theater.service.TheaterExistenceChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final MovieExistenceChecker movieExistenceChecker;
    private final TheaterExistenceChecker theaterExistenceChecker;

    public List<ScheduleDto> getSchedules() {
        return scheduleRepository.findAll().stream().map(scheduleMapper::toDto).toList();
    }

    public Optional<ScheduleDto> getSchedule(@NonNull final String id) {
        return scheduleRepository.findById(id).map(scheduleMapper::toDto);
    }

    public ScheduleDto createSchedule(@NonNull final ScheduleDto scheduleDto) {
        if (scheduleDto.getId() != null) {
            throw new IllegalArgumentException("Id must be null");
        }
        validateIntegrity(scheduleDto);
        return scheduleMapper.toDto(scheduleRepository.save(scheduleMapper.toEntity(scheduleDto)));
    }

    public ScheduleDto updateSchedule(@NonNull final String id, @NonNull final ScheduleDto scheduleDto) {
        if (scheduleDto.getId() != null && !scheduleDto.getId().equals(id)) {
            throw new IllegalArgumentException("Id must be null or match the path");
        }

        return scheduleRepository.findById(id).map(scheduleEntity -> {
            validateIntegrity(scheduleDto);
            scheduleMapper.updateEntity(scheduleEntity, scheduleDto);
            scheduleRepository.save(scheduleEntity);
            return scheduleMapper.toDto(scheduleEntity);
        }).orElseThrow(() -> new IllegalArgumentException("Schedule with id " + id + " not found"));
    }

    public void deleteSchedule(@NonNull final String id) {
        scheduleRepository.deleteById(id);
    }

    private void validateIntegrity(@NonNull final ScheduleDto scheduleDto) {
        if (!movieExistenceChecker.isMovieExisting(scheduleDto.getMovieId())) {
            throw new IllegalArgumentException("Movie with id " + scheduleDto.getMovieId() + " does not exist");
        }
        if (!theaterExistenceChecker.isTheaterExisting(scheduleDto.getTheaterId())) {
            throw new IllegalArgumentException("Theater with id " + scheduleDto.getTheaterId() + " does not exist");
        }
    }
}
