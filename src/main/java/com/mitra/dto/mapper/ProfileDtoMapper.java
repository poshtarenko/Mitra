package com.mitra.dto.mapper;

import com.mitra.cloud.CloudStorageProvider;
import com.mitra.dto.*;
import com.mitra.entity.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileDtoMapper implements DtoMapper<ProfileDto, Profile> {

    private final DtoMapper<LocationDto, Location> locationDtoMapper;
    private final DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper;
    private final DtoMapper<SpecialityDto, Speciality> specialityDtoMapper;
    private final CloudStorageProvider cloudStorageProvider;

    public ProfileDtoMapper(DtoMapper<LocationDto, Location> locationDtoMapper, DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper,
                            DtoMapper<SpecialityDto, Speciality> specialityDtoMapper, CloudStorageProvider cloudStorageProvider) {
        this.locationDtoMapper = locationDtoMapper;
        this.instrumentDtoMapper = instrumentDtoMapper;
        this.specialityDtoMapper = specialityDtoMapper;
        this.cloudStorageProvider = cloudStorageProvider;
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

        String photoPath = dto.getPhotoPath();
        try {
            if (dto.getPhotoContent() != null && dto.getPhotoContent().available() > 0) {
                if (photoPath != null && !photoPath.equals(""))
                    cloudStorageProvider.deleteFile(photoPath);
                photoPath = cloudStorageProvider.setProfilePhoto(dto.getId(), dto.getPhotoContent());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Profile.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .text(dto.getText())
                .photoPath(photoPath)
                .location(location)
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

//        InputStream photoContent = null;
        String photoPath = entity.getPhotoPath();
//        if (photoPath != null && !photoPath.equals(""))
//            photoContent = cloudStorageProvider.getImage(entity.getPhotoPath());

        return ProfileDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .gender(entity.getGender())
                .text(entity.getText())
                .photoPath(photoPath)
//                .photoContent(photoContent)
                .location(locationDto)
                .instruments(instrumentDtos)
                .specialities(specialityDtos)
                .build();
    }
}
