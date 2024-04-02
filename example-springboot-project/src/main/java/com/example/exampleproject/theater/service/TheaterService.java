package com.example.exampleproject.theater.service;

import com.example.exampleproject.theater.persistence.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheaterService implements TheaterExistenceChecker {
    private final TheaterRepository theaterRepository;
    private final TheaterMapper theaterMapper;

    public List<TheaterDto> getTheaters() {
        return theaterRepository.findAll().stream().map(theaterMapper::toDto).toList();
    }

    public Optional<TheaterDto> getTheater(@NonNull final String id) {
        return theaterRepository.findById(id).map(theaterMapper::toDto);
    }

    public TheaterDto createTheater(@NonNull final TheaterDto theaterDto) {
        if (theaterDto.getName() != null) {
            throw new IllegalArgumentException("Id must be null");
        }
        return theaterMapper.toDto(theaterRepository.save(theaterMapper.toEntity(theaterDto)));
    }

    public TheaterDto updateTheater(@NonNull final String id, @NonNull final TheaterDto theaterDto) {
        if (theaterDto.getName() != null && !theaterDto.getName().equals(id)) {
            throw new IllegalArgumentException("Id must be null or match the path");
        }

        return theaterRepository.findById(id).map(theaterEntity -> {
            theaterMapper.updateEntity(theaterEntity, theaterDto);
            theaterRepository.save(theaterEntity);
            return theaterMapper.toDto(theaterEntity);
        }).orElseThrow(() -> new IllegalArgumentException("Theater with id " + id + " not found"));
    }

    public void deleteTheater(@NonNull final String id) {
        theaterRepository.deleteById(id);
    }

    @Override
    public boolean isTheaterExisting(final String theaterId) {
        return theaterRepository.existsById(theaterId);
    }
}
