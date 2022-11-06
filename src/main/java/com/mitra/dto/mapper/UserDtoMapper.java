package com.mitra.dto.mapper;

import com.mitra.dto.UserDto;
import com.mitra.entity.User;

import java.sql.Connection;

public class UserDtoMapper implements DtoMapper<UserDto, User> {

    @Override
    public User mapToEntity(UserDto dto) {
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
