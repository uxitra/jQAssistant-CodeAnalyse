package com.example.exampleproject.schedule.service;

import com.example.exampleproject.schedule.persistence.ScheduleEntity;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleMapper {

    public ScheduleDto toDto(@NonNull final ScheduleEntity scheduleEntity) {
        return new ScheduleDto(scheduleEntity.getId(),
                               scheduleEntity.getMovieId(),
                               scheduleEntity.getTheaterId(),
                               scheduleEntity.getStartTime());
    }

    public ScheduleEntity toEntity(@NonNull final ScheduleDto scheduleDto) {
        return new ScheduleEntity(scheduleDto.getId(),
                                  scheduleDto.getMovieId(),
                                  scheduleDto.getTheaterId(),
                                  scheduleDto.getStartTime());
    }

    public void updateEntity(@NonNull final ScheduleEntity scheduleEntity, @NonNull final ScheduleDto scheduleDto) {
        scheduleEntity.setMovieId(scheduleDto.getMovieId());
        scheduleEntity.setTheaterId(scheduleDto.getTheaterId());
        scheduleEntity.setStartTime(scheduleDto.getStartTime());
    }
}
