package com.mitra.service;

import com.mitra.dto.UserDto;
import com.mitra.entity.Role;

import java.util.Optional;

public interface UserService {

    // TODO : comments

    /**
     * Try login
     *
     * @param email    email
     * @param password password
     * @return optional of userDto
     */
    Optional<UserDto> tryLogin(String email, String password);

    boolean register(String email, String password);

    void changePassword(UserDto userDto, String newPassword);

    void changeRole(UserDto userDto, Role role);

    void upgradeToPremium(UserDto userDto);

    void ban(UserDto userDto);
}
