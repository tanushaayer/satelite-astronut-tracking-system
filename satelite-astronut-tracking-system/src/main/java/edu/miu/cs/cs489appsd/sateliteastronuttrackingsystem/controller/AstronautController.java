package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.controller;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request.AstronautRequestDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response.AstronautResponseDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.service.AstronautService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/astronauts")
@RequiredArgsConstructor
public class AstronautController {

    private final AstronautService astronautService;


    @PostMapping
    public ResponseEntity<AstronautResponseDto> addAstronaut(@Valid @RequestBody AstronautRequestDto astronautRequestDto) {
        AstronautResponseDto created = astronautService.addAstronaut(astronautRequestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AstronautResponseDto>> getAllAstronauts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "experienceYears") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Page<AstronautResponseDto> astronauts = astronautService.getAllAstronautsPaginated(page, size, sortDirection, sortBy);
        return ResponseEntity.ok(astronauts);
    }

}
