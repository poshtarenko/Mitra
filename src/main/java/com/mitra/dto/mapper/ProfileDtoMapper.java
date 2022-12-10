package com.mitra.dto.mapper;

import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.entity.Instrument;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;
import com.mitra.entity.Speciality;
import com.mitra.entity.impl.ProfileImpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileDtoMapper implements DtoMapper<ProfileDto, Profile> {

    private final DtoMapper<LocationDto, Location> locationDtoMapper;
    private final DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper;
    private final DtoMapper<SpecialityDto, Speciality> specialityDtoMapper;

    public ProfileDtoMapper(DtoMapper<LocationDto, Location> locationDtoMapper,
                            DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper,
                            DtoMapper<SpecialityDto, Speciality> specialityDtoMapper) {
        this.locationDtoMapper = locationDtoMapper;
        this.instrumentDtoMapper = instrumentDtoMapper;
        this.specialityDtoMapper = specialityDtoMapper;
    }

    @Override
    public Profile mapToEntity(ProfileDto dto) {
        Location location = null;
        if (dto.getLocation() != null)
            location = locationDtoMapper.mapToEntity(dto.getLocation());

        List<Instrument> instruments;
        if (dto.getInstruments() != null) {
            instruments = dto.getInstruments().stream()
                    .map(instrumentDtoMapper::mapToEntity)
                    .collect(Collectors.toList());
        } else
            instruments = Collections.emptyList();

        List<Speciality> specialities;
        if (dto.getInstruments() != null) {
            specialities = dto.getSpecialities().stream()
                    .map(specialityDtoMapper::mapToEntity)
                    .collect(Collectors.toList());
        } else
            specialities = Collections.emptyList();

        return ProfileImpl.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .text(dto.getText())
                .photoPath(dto.getPhotoPath())
                .location(location)
                .previewTrack(dto.getPreviewTrack())
                .instruments(instruments)
                .specialities(specialities)
                .build();
    }

    @Override
    public ProfileDto mapToDto(Profile entity) {
        LocationDto locationDto = null;
        if (entity.getLocation() != null)
            locationDto = locationDtoMapper.mapToDto(entity.getLocation());

        List<InstrumentDto> instrumentDtos;
        if (entity.getInstruments() != null)
            instrumentDtos = entity.getInstruments().stream()
                    .map(instrumentDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        else
            instrumentDtos = Collections.emptyList();

        List<SpecialityDto> specialityDtos;
        if (entity.getSpecialities() != null)
            specialityDtos = entity.getSpecialities().stream()
                    .map(specialityDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        else
            specialityDtos = Collections.emptyList();

        return ProfileDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .gender(entity.getGender())
                .text(entity.getText())
                .photoPath(entity.getPhotoPath())
                .location(locationDto)
                .previewTrack(entity.getPreviewTrack())
                .instruments(instrumentDtos)
                .specialities(specialityDtos)
                .build();
    }
}
