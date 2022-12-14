package com.mitra.service;

import com.mitra.dto.CredentialsDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Role;
import com.mitra.exception.ValidationException;

import java.util.Optional;

public interface UserService {

    /**
     * Find user by ID
     *
     * @param id user id
     * @return optional of userDto
     */
    Optional<UserDto> find(int id);

    /**
     * Try login
     *
     * @param credentials email and password
     * @return optional of userDto
     */
    Optional<UserDto> find(CredentialsDto credentials) throws ValidationException;

    /**
     * Try register
     *
     * @param credentials email and password
     * @return true if registered, false if not
     */
    boolean register(CredentialsDto credentials) throws ValidationException;

    /**
     * Change user email
     *
     * @param userId   user id
     * @param newEmail new email
     */
    void changeEmail(int userId, String newEmail) throws ValidationException;

    /**
     * Change user password
     *
     * @param userId      user id
     * @param newPassword new password
     */
    void changePassword(int userId, String newPassword) throws ValidationException;

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
