package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.UserDao;
import com.mitra.dto.UserDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Role;
import com.mitra.entity.User;
import com.mitra.entity.impl.UserImpl;
import com.mitra.exception.ValidationException;
import com.mitra.security.PasswordEncryptor;
import com.mitra.service.UserService;
import com.mitra.validator.Validator;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final DtoMapper<UserDto, User> userDtoMapper;
    private final Validator<UserDto> userDtoValidator;
    private final PasswordEncryptor passwordEncryptor;

    public UserServiceImpl(UserDao userDao, DtoMapper<UserDto, User> userDtoMapper, Validator<UserDto> userDtoValidator, PasswordEncryptor passwordEncryptor) {
        this.userDao = userDao;
        this.userDtoMapper = userDtoMapper;
        this.userDtoValidator = userDtoValidator;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public Optional<UserDto> tryLogin(String email, String password) {
        try (Connection connection = ConnectionManager.get()) {
            String encryptedPassword = encryptPassword(password);
            Optional<User> user = userDao.find(connection, email, encryptedPassword);

            log.info("User logged in : {}", email);
            return user.map(userDtoMapper::mapToDto);
        } catch (SQLException e) {
            log.error("Login failed");
            return Optional.empty();
        }
    }

    @Override
    public boolean register(String email, String password) {
        try (Connection connection = ConnectionManager.get()) {
            //checkUserDtoIsValid(userDto);
            String encryptedPassword = encryptPassword(password);

            User user = new UserImpl(
                    0,
                    email,
                    encryptedPassword,
                    Role.USER
            );

            int createdUserId = userDao.save(connection, user);

            log.info("User registered : email={}, id={}", email, createdUserId);
            return true;
        } catch (SQLException e) {
            log.error("Registration failed");
            return false;
        }
    }

    @Override
    public void changePassword(UserDto userDto, String newPassword) {

    }

    @Override
    public void changeRole(UserDto userDto, Role role) {

    }

    @Override
    public void upgradeToPremium(UserDto userDto) {

    }

    @Override
    public void ban(UserDto userDto) {

    }

    private void checkUserDtoIsValid(UserDto userDto) {
        if (!userDtoValidator.isValid(userDto)) {
            throw new ValidationException(userDto + " is invalid.");
        }
    }

    private String encryptPassword(String password) {
        return passwordEncryptor.encrypt(password);
    }
}
