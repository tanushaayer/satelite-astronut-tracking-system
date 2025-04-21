package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.common.OrbitType;

import java.time.LocalDate;

public record SatelliteResponseDto(
        Long id,
        String name,
        LocalDate launchDate,
        OrbitType orbitType,
        boolean decommissioned
) {
}
