package com.mitra.dto.mapper;

import com.mitra.dto.ProfileDto;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;
import com.mitra.exception.ServiceException;
import com.mitra.service.LocationService;
import com.mitra.service.impl.LocationServiceImpl;

public class ProfileDtoMapper implements DtoMapper<ProfileDto, Profile> {

    private static final ProfileDtoMapper INSTANCE = new ProfileDtoMapper();
    private static final LocationService locationService = LocationServiceImpl.getInstance();

    private ProfileDtoMapper(){}

    public static ProfileDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Profile mapToEntity(ProfileDto dto) {
        Location location = locationService.getByCity(dto.getLocation())
                .orElse(locationService.getById(1)
                        .orElseThrow(() -> new ServiceException("There must be any city with id = 1, but it doesn't")));

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
        return ProfileDto.builder()
                .name(entity.getName())
                .age(entity.getAge())
                .gender(entity.getGender())
                .text(entity.getText())
                .location(entity.getLocation().getCity())
                .build();
    }
}
