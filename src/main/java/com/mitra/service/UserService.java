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

    /**
     * Try register
     *
     * @param email    email
     * @param password password
     * @return true if registered, false if not
     */
    boolean register(String email, String password);

    /**
     * Change user password
     *
     * @param userId      user id
     * @param newPassword new password
     */
    void changePassword(int userId, String newPassword);

    /**
     * Change user role
     *
     * @param userId user id
     * @param role   new role
     */
    void changeRole(int userId, Role role);

    /**
     * Upgrade user to premium
     *
     * @param userId user id
     */
    void upgradeToPremium(int userId);

    /**
     * Ban user
     *
     * @param userId user id
     */
    void ban(int userId);
}
