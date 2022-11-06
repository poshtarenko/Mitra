package com.mitra.dto.mapper;

import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;
import com.mitra.exception.ServiceException;
import com.mitra.service.LocationService;
import com.mitra.service.ServiceFactory;

import java.sql.Connection;

public class ProfileDtoMapper implements DtoMapper<ProfileDto, Profile> {

    private DtoMapper<LocationDto, Location> locationDtoMapper;

    public ProfileDtoMapper(DtoMapper<LocationDto, Location> locationDtoMapper) {
        this.locationDtoMapper = locationDtoMapper;
    }

    @Override
    public Profile mapToEntity(ProfileDto dto) {
        Location location = locationDtoMapper.mapToEntity(dto.getLocation());

        return Profile.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .text(dto.getText())
                .location(location)
                .build();
    }

    @Override
    public ProfileDto mapToDto(Profile entity) {
        LocationDto locationDto = locationDtoMapper.mapToDto(entity.getLocation());

        return ProfileDto.builder()
                .name(entity.getName())
                .age(entity.getAge())
                .gender(entity.getGender())
                .text(entity.getText())
                .location(locationDto)
                .build();
    }
}
