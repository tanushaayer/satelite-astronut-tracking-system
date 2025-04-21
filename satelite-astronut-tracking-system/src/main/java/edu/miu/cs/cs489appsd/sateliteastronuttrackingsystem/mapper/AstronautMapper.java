package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.mapper;

import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.request.AstronautRequestDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.dto.response.AstronautResponseDto;
import edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.entity.Astronaut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {SatelliteMapper.class})
public interface AstronautMapper {

    @Mapping(source = "satelliteRequestDtoList", target = "satellites")
    Astronaut astronautRequestDtoToAstronaut(AstronautRequestDto astronautRequestDto);

    @Mapping(source = "satellites", target = "satelliteResponseDtoList")
    AstronautResponseDto astronautToAstronautResponseDto(Astronaut astronaut);

    @Mapping(source = "satellites", target = "satelliteResponseDtoList")
    List<AstronautResponseDto> astronautListToAstronautResponseDtoList(List<Astronaut> astronautList);

}
