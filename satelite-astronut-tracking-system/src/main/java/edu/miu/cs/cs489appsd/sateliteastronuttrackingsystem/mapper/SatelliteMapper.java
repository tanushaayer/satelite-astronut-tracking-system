package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.mapper;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request.SatelliteRequestDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response.SatelliteResponseDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.entity.Satellite;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SatelliteMapper {

    //dto to entity
    Satellite satelliteRequestDtoToSatellite(SatelliteRequestDto satelliteRequestDto);

    SatelliteResponseDto satelliteToSatelliteResponseDto(Satellite satellite);

    List<SatelliteResponseDto> satelliteListToSatelliteResponseDtoList(List<Satellite> satellites);

}
