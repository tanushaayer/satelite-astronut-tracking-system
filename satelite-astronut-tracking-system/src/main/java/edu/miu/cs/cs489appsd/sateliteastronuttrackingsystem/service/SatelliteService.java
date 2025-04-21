package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.service;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request.SatelliteRequestDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response.SatelliteResponseDto;

import java.util.List;

public interface SatelliteService {
    SatelliteResponseDto addSatellite(SatelliteRequestDto satelliteRequestDto);
    SatelliteResponseDto getSatelliteById(Long id);
    SatelliteResponseDto getSatelliteByName(String satelliteName);
    List<SatelliteResponseDto> getAllSatellites();
    SatelliteResponseDto updateSatellite(Long id , SatelliteRequestDto satelliteRequestDto);
}
