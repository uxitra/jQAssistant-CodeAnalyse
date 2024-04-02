package com.example.exampleproject.theater.web;

import com.example.exampleproject.theater.service.TheaterDto;
import com.example.exampleproject.theater.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/theaters")
@RequiredArgsConstructor
class TheaterController {

    private final TheaterService theaterService;

    @GetMapping
    public List<TheaterDto> getTheaters() {
        return theaterService.getTheaters();
    }

    @GetMapping("/{id}")
    public TheaterDto getTheater(@PathVariable(name = "id") final String id) {
        return theaterService.getTheater(id)
                             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theater not found"));
    }

    @PostMapping
    public TheaterDto createTheater(@RequestBody final TheaterDto theaterDto) {
        return theaterService.createTheater(theaterDto);
    }

    @PutMapping("/{id}")
    public TheaterDto updateTheater(@PathVariable(name = "id") final String id,
                                    @RequestBody final TheaterDto theaterDto) {
        return theaterService.updateTheater(id, theaterDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(@PathVariable(name = "id") final String id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }
}
