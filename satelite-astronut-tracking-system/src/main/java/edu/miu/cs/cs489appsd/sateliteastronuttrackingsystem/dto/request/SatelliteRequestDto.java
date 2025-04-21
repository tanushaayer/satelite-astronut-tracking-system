package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.common.OrbitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record SatelliteRequestDto(
        @NotBlank
        String name,

        @Past(message = "Launch date must be in the past")
        LocalDate launchDate,

        @NotNull
        OrbitType orbitType,

        @NotNull
        boolean decommissioned
) {
}
