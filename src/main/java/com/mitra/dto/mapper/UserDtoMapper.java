package com.mitra.dto.mapper;

import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Profile;
import com.mitra.entity.User;

import java.sql.Connection;

public class UserDtoMapper implements DtoMapper<UserDto, User> {

    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;

    public UserDtoMapper(DtoMapper<ProfileDto, Profile> profileDtoMapper) {
        this.profileDtoMapper = profileDtoMapper;
    }

    @Override
    public User mapToEntity(UserDto dto) {
        return new User(
                dto.getId(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getRole(),
                profileDtoMapper.mapToEntity(dto.getProfile())
        );
    }

    @Override
    public UserDto mapToDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .profile(profileDtoMapper.mapToDto(entity.getProfile()))
                .build();
    }
}
