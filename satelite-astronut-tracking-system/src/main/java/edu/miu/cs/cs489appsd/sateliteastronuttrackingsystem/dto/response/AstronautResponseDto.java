package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response;

import java.util.List;

public record AstronautResponseDto(
        Long id,
        String firstName,
        String lastName,
        int experienceYears,
        List<SatelliteResponseDto> satelliteResponseDtoList
) {
}
