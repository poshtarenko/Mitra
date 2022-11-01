package com.mitra.dto.mapper;

import com.mitra.db.mapper.UserRowMapper;
import com.mitra.dto.UserDto;
import com.mitra.entity.User;

public class UserDtoMapper implements DtoMapper<UserDto, User> {

    private static final UserDtoMapper INSTANCE = new UserDtoMapper();

    private UserDtoMapper(){}

    public static UserDtoMapper getInstance() {
        return INSTANCE;
    }

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
