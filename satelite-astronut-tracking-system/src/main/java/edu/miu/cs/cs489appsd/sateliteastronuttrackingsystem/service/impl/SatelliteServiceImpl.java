package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.service.impl;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request.SatelliteRequestDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response.SatelliteResponseDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.entity.Satellite;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.exception.satellite.SatelliteNotFoundException;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.mapper.SatelliteMapper;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.repository.SatelliteRepository;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.service.SatelliteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SatelliteServiceImpl implements SatelliteService {
    private final SatelliteRepository satelliteRepository;
    private final SatelliteMapper satelliteMapper;

    @Override
    public SatelliteResponseDto addSatellite(SatelliteRequestDto satelliteRequestDto) {
        Satellite satellite = satelliteMapper.satelliteRequestDtoToSatellite(satelliteRequestDto);
        Satellite savedSatellite = satelliteRepository.save(satellite);
        return satelliteMapper.satelliteToSatelliteResponseDto(savedSatellite);
    }

    @Override
    public SatelliteResponseDto getSatelliteById(Long id) {
        Optional<Satellite> satelliteOptional = satelliteRepository.findById(id);
        if (satelliteOptional.isPresent()) {
            Satellite satellite = satelliteOptional.get();
            return satelliteMapper.satelliteToSatelliteResponseDto(satellite);
        }else{
            throw new SatelliteNotFoundException("Satellite with id " + id + " not found");
        }
    }

    @Override
    public SatelliteResponseDto getSatelliteByName(String satelliteName) {
        Optional<Satellite> satelliteOptional = satelliteRepository.findByName(satelliteName);
        if (satelliteOptional.isPresent()) {
            Satellite satellite = satelliteOptional.get();
            return satelliteMapper.satelliteToSatelliteResponseDto(satellite);
        }else{
            throw new SatelliteNotFoundException("Satellite with name " + satelliteName + " not found");
        }
    }

    @Override
    public List<SatelliteResponseDto> getAllSatellites() {
        List<Satellite> satelliteList = satelliteRepository.findAll();
        return satelliteMapper.satelliteListToSatelliteResponseDtoList(satelliteList);
    }

    @Override
    @Transactional
    public SatelliteResponseDto updateSatellite(Long id, SatelliteRequestDto satelliteRequestDto) {
        Optional<Satellite> satelliteOptional = satelliteRepository.findById(id);

        if (satelliteOptional.isPresent()) {
            Satellite foundSatellite = satelliteOptional.get();

            if (foundSatellite.isDecommissioned()) {
                throw new IllegalStateException("Cannot update a decommissioned satellite: " + id);
            }

            Satellite mappedSatellite = satelliteMapper.satelliteRequestDtoToSatellite(satelliteRequestDto);
            mappedSatellite.setId(foundSatellite.getId());

            Satellite updatedSatellite = satelliteRepository.save(mappedSatellite);
            return satelliteMapper.satelliteToSatelliteResponseDto(updatedSatellite);
        } else {
            throw new SatelliteNotFoundException("Satellite with id " + id + " not found");
        }
    }

}
