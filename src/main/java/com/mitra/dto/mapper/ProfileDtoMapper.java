package com.mitra.dto.mapper;

import com.mitra.cloud.CloudStorageProvider;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.entity.Instrument;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;
import com.mitra.entity.Speciality;

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
        Location location = locationDtoMapper.mapToEntity(dto.getLocation());

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
        if (dto.getPhotoContent() != null) {
            if (!"".equals(photoPath))
                cloudStorageProvider.deleteFile(photoPath);
            photoPath = cloudStorageProvider.setProfilePhoto(dto.getId(), dto.getPhotoContent());
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
        LocationDto locationDto = locationDtoMapper.mapToDto(entity.getLocation());

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

        InputStream photoContent = null;
        if (entity.getPhotoPath() != null)
            photoContent = cloudStorageProvider.getImage(entity.getPhotoPath());

        return ProfileDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .gender(entity.getGender())
                .text(entity.getText())
                .photoPath(entity.getPhotoPath())
                .photoContent(photoContent)
                .location(locationDto)
                .instruments(instrumentDtos)
                .specialities(specialityDtos)
                .build();
    }
}
