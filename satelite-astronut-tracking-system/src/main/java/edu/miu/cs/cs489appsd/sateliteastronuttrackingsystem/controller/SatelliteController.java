package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.controller;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request.SatelliteRequestDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response.SatelliteResponseDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.service.SatelliteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/satellites")
@RequiredArgsConstructor
public class SatelliteController {
    private final SatelliteService satelliteService;

    @PutMapping("/{id}")
    public ResponseEntity<SatelliteResponseDto> updateSatellite(
            @PathVariable Long id,
            @Valid @RequestBody SatelliteRequestDto satelliteRequestDto
    ) {
        SatelliteResponseDto updatedSatellite = satelliteService.updateSatellite(id, satelliteRequestDto);
        return ResponseEntity.ok(updatedSatellite);
    }
}
