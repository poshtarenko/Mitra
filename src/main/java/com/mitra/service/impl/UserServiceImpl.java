package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.UserDao;
import com.mitra.dto.UserDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Role;
import com.mitra.entity.User;
import com.mitra.entity.impl.UserImpl;
import com.mitra.exception.DaoException;
import com.mitra.exception.ValidationException;
import com.mitra.security.PasswordEncryptor;
import com.mitra.service.UserService;
import com.mitra.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final DtoMapper<UserDto, User> userDtoMapper;
    private final UserValidator userValidator;
    private final PasswordEncryptor passwordEncryptor;

    public UserServiceImpl(UserDao userDao, DtoMapper<UserDto, User> userDtoMapper, UserValidator userValidator, PasswordEncryptor passwordEncryptor) {
        this.userDao = userDao;
        this.userDtoMapper = userDtoMapper;
        this.userValidator = userValidator;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public Optional<UserDto> tryLogin(String email, String password) {
        try (Connection connection = ConnectionManager.get()) {
            checkCredentialsOrThrowException(email, password);

            String encryptedPassword = passwordEncryptor.encrypt(password);
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
            checkCredentialsOrThrowException(email, password);

            String encryptedPassword = passwordEncryptor.encrypt(password);

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
    public void changePassword(int userId, String newPassword) {
        try (Connection connection = ConnectionManager.get()) {
            String encryptedPassword = passwordEncryptor.encrypt(newPassword);
            userDao.changePassword(connection, userId, encryptedPassword);
        } catch (SQLException e) {
            log.error("Password change failed");
        }
    }

    @Override
    public void changeRole(int userId, Role role) {
        try (Connection connection = ConnectionManager.get()) {
            userDao.changeRole(connection, userId, role);
        } catch (SQLException e) {
            log.error("Role change failed");
        }
    }

    @Override
    public void upgradeToPremium(int userId) {
        try (Connection connection = ConnectionManager.get()) {
            User user = userDao.find(connection, userId)
                    .orElseThrow(() -> new DaoException("User with id " + userId + " not found"));

            if (user.getRole() == Role.USER)
                userDao.changeRole(connection, userId, Role.USER_PR);
            else
                log.warn("Trying upgrade to premium user which has not 'USER' role");
        } catch (SQLException | DaoException e) {
            log.error("Upgrade to premium failed");
        }
    }

    @Override
    public void ban(int userId) {
        try (Connection connection = ConnectionManager.get()) {
            User user = userDao.find(connection, userId)
                    .orElseThrow(() -> new DaoException("User with id " + userId + " not found"));

            if (user.getRole() == Role.ADMIN)
                userDao.changeRole(connection, userId, Role.BANNED);
            else
                log.warn("Trying to ban admin");
        } catch (SQLException | DaoException e) {
            log.error("Ban failed");
        }
    }


    private void checkCredentialsOrThrowException(String email, String password) {
        if (!userValidator.emailIsValid(email) || !userValidator.passwordIsValid(password))
            return;
        throw new ValidationException("Credentials are invalid");
    }
}
