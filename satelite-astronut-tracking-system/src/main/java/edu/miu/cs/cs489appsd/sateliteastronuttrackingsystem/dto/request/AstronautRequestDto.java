package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record AstronautRequestDto(
        @NotBlank
        @Size(min = 2, max = 20)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 20)
        String lastName,

        @Min(0)
        @Max(50)
        int experienceYears,

        @NotNull
        @Size(min = 1, message = "At least one satellite ID must be provided")
        List<@Valid SatelliteRequestDto> satelliteRequestDtoList
) {

}
