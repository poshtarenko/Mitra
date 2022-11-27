package com.mitra.dto.mapper;

import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Profile;
import com.mitra.entity.User;
import com.mitra.entity.impl.ProfileImpl;
import com.mitra.entity.impl.UserImpl;

public class UserDtoMapper implements DtoMapper<UserDto, User> {

    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;

    public UserDtoMapper(DtoMapper<ProfileDto, Profile> profileDtoMapper) {
        this.profileDtoMapper = profileDtoMapper;
    }

    @Override
    public User mapToEntity(UserDto dto) {
        Profile profile = null;
        if (dto.getProfile() != null)
            profile = profileDtoMapper.mapToEntity(dto.getProfile());

        return new UserImpl(
                dto.getId(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getRole(),
                profile
        );
    }

    @Override
    public UserDto mapToDto(User entity) {
        ProfileDto profile = null;
        if (entity.getProfile() != null)
            profile = profileDtoMapper.mapToDto(entity.getProfile());

        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .profile(profile)
                .build();
    }
}
