package com.mitra.dto.mapper;

import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Instrument;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileDtoMapper implements DtoMapper<ProfileDto, Profile> {

    private DtoMapper<LocationDto, Location> locationDtoMapper;
    private DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper;

    public ProfileDtoMapper(DtoMapper<LocationDto, Location> locationDtoMapper, DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper) {
        this.locationDtoMapper = locationDtoMapper;
        this.instrumentDtoMapper = instrumentDtoMapper;
    }

    @Override
    public Profile mapToEntity(ProfileDto dto) {
        Location location = locationDtoMapper.mapToEntity(dto.getLocation());

        List<Instrument> instruments;
        if (dto.getInstruments() != null) {
            instruments = dto.getInstruments().stream()
                    .map(instrumentDtoMapper::mapToEntity)
                    .collect(Collectors.toList());
        } else
            instruments = Collections.emptyList();

        return Profile.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .text(dto.getText())
                .location(location)
                .instruments(instruments)
                .build();
    }

    @Override
    public ProfileDto mapToDto(Profile entity) {
        LocationDto locationDto = locationDtoMapper.mapToDto(entity.getLocation());
        List<InstrumentDto> instrumentDtos;
        if (entity.getInstruments() != null)
            instrumentDtos = entity.getInstruments().stream()
                    .map(instrumentDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        else
            instrumentDtos = Collections.emptyList();

        return ProfileDto.builder()
                .name(entity.getName())
                .age(entity.getAge())
                .gender(entity.getGender())
                .text(entity.getText())
                .location(locationDto)
                .instruments(instrumentDtos)
                .build();
    }
}
