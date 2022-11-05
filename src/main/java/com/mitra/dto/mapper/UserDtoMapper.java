package com.mitra.dto.mapper;

import com.mitra.dto.UserDto;
import com.mitra.entity.User;

import java.sql.Connection;

public class UserDtoMapper implements DtoMapper<UserDto, User> {

    private static final UserDtoMapper INSTANCE = new UserDtoMapper();

    private UserDtoMapper() {
    }

    public static UserDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User mapToEntity(Connection connection, UserDto dto) {
        return new User(
                dto.getId(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getRole()
        );
    }

    @Override
    public UserDto mapToDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();
    }
}
