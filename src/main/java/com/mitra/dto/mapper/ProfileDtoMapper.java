package com.mitra.dto.mapper;

import com.mitra.dto.ProfileDto;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;
import com.mitra.exception.ServiceException;
import com.mitra.service.LocationService;
import com.mitra.service.ServiceFactory;

import java.sql.Connection;

public class ProfileDtoMapper implements DtoMapper<ProfileDto, Profile> {

    private static final ProfileDtoMapper INSTANCE = new ProfileDtoMapper();
    private static final LocationService locationService = ServiceFactory.getInstance().getLocationService();

    private ProfileDtoMapper() {
    }

    public static ProfileDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Profile mapToEntity(Connection connection, ProfileDto dto) {
        Location location = locationService.getByCity(connection, dto.getLocation())
                .orElseThrow(() -> new ServiceException("City " + dto.getLocation() + " not found"));

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
