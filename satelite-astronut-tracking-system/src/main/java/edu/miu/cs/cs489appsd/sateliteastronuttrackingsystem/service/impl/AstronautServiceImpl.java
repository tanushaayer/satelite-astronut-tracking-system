package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.service.impl;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request.AstronautRequestDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response.AstronautResponseDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.entity.Astronaut;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.entity.Satellite;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.exception.astronaut.AstronautNotFoundException;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.mapper.AstronautMapper;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.mapper.SatelliteMapper;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.repository.AstronautRepository;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.repository.SatelliteRepository;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.service.AstronautService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AstronautServiceImpl implements AstronautService {
    private final AstronautRepository astronautRepository;
    private final SatelliteRepository satelliteRepository;
    private final AstronautMapper astronautMapper;
    private final SatelliteMapper satelliteMapper;

    @Override
    @Transactional
    public AstronautResponseDto addAstronaut(AstronautRequestDto astronautRequestDto) {
        Astronaut mappedAstronaut = astronautMapper.astronautRequestDtoToAstronaut(astronautRequestDto);

        List<Satellite> attachedSatellites = mappedAstronaut.getSatellites().stream().map(satellite -> {
            Optional<Satellite> optionalSatellite = satelliteRepository.findByName(satellite.getName());
            return optionalSatellite.orElseGet(() -> satelliteRepository.save(satellite));
        }).toList();

        mappedAstronaut.setSatellites(new ArrayList<>(attachedSatellites));
        Astronaut savedAstronaut = astronautRepository.save(mappedAstronaut);

        return astronautMapper.astronautToAstronautResponseDto(savedAstronaut);
    }


    @Override
    public AstronautResponseDto getAstronautById(Long id) {
        Astronaut astronaut = astronautRepository.findById(id)
                .orElseThrow(() -> new AstronautNotFoundException("Astronaut not found with ID: " + id));
        return astronautMapper.astronautToAstronautResponseDto(astronaut);
    }

    @Override
    public List<AstronautResponseDto> getAllAstronauts() {
        return astronautRepository.findAll().stream()
                .map(astronautMapper::astronautToAstronautResponseDto)
                .toList();
    }

    @Override
    public AstronautResponseDto updateAstronaut(Long id, AstronautRequestDto astronautRequestDto) {
        Astronaut astronaut = astronautRepository.findById(id)
                .orElseThrow(() -> new AstronautNotFoundException("Astronaut not found with ID: " + id));

        astronaut.setFirstName(astronautRequestDto.firstName());
        astronaut.setLastName(astronautRequestDto.lastName());
        astronaut.setExperienceYears(astronautRequestDto.experienceYears());

        List<Satellite> satellites = astronautRequestDto.satelliteRequestDtoList().stream()
                .map(satelliteMapper::satelliteRequestDtoToSatellite)
                .toList();

        // Check if any satellite is decommissioned
        satellites.forEach(satellite -> {
            if (satellite.isDecommissioned()) {
                throw new IllegalStateException("Cannot assign decommissioned satellite: " + satellite.getName());
            }
        });

        astronaut.setSatellites(new ArrayList<>(satellites));
        return astronautMapper.astronautToAstronautResponseDto(astronautRepository.save(astronaut));
    }

    @Override
    public Page<AstronautResponseDto> getAllAstronautsPaginated(int page, int size, String sortDirection, String sortBy) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Astronaut> astronautPage = astronautRepository.findAll(pageable);
        return astronautPage.map(astronautMapper::astronautToAstronautResponseDto);
    }

}
