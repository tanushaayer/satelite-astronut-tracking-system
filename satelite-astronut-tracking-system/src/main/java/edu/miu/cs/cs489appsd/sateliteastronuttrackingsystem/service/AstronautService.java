package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.service;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request.AstronautRequestDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response.AstronautResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AstronautService {
    AstronautResponseDto addAstronaut(AstronautRequestDto astronautRequestDto);
    AstronautResponseDto getAstronautById(Long id);
    List<AstronautResponseDto> getAllAstronauts();
    AstronautResponseDto updateAstronaut(Long id, AstronautRequestDto astronautRequestDto);
    Page<AstronautResponseDto> getAllAstronautsPaginated(int page, int size, String sortDirection, String sortBy);

}
