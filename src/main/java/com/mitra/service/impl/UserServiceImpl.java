package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.UserDao;
import com.mitra.dto.CredentialsDto;
import com.mitra.dto.UserDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Role;
import com.mitra.entity.User;
import com.mitra.entity.impl.UserImpl;
import com.mitra.exception.DaoException;
import com.mitra.exception.ValidationException;
import com.mitra.security.PasswordEncryptor;
import com.mitra.service.UserService;
import com.mitra.validator.Error;
import com.mitra.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public Optional<UserDto> find(int id) {
        try (Connection connection = ConnectionManager.get()) {
            Optional<User> user = userDao.find(connection, id);
            return user.map(userDtoMapper::mapToDto);
        } catch (SQLException | DaoException e) {
            log.error("Find user by id is failed", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserDto> find(CredentialsDto credentials) {
        try (Connection connection = ConnectionManager.get()) {
            checkCredentialsOrThrowException(credentials.getEmail(), credentials.getPassword());

            String encryptedPassword = passwordEncryptor.encrypt(credentials.getPassword());
            Optional<User> user = userDao.findByEmail(connection, credentials.getEmail(), encryptedPassword);

            log.info("User logged in : {}", credentials.getEmail());
            return user.map(userDtoMapper::mapToDto);
        } catch (SQLException e) {
            log.error("Login failed", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean register(CredentialsDto credentials) {
        try (Connection connection = ConnectionManager.get()) {
            checkCredentialsOrThrowException(credentials.getEmail(), credentials.getPassword());

            if (userDao.findByEmail(connection, credentials.getEmail()).isPresent())
                throw new ValidationException(Collections.singletonList(
                        Error.of("Email already registered", "Trying to register with used email")));

            String encryptedPassword = passwordEncryptor.encrypt(credentials.getPassword());

            User user = new UserImpl(
                    0,
                    credentials.getEmail(),
                    encryptedPassword,
                    Role.USER
            );

            int createdUserId = userDao.save(connection, user);

            log.info("User registered : email={}, id={}", credentials.getEmail(), createdUserId);
            return true;
        } catch (SQLException | DaoException e) {
            log.error("Registration failed", e);
            return false;
        }
    }

    @Override
    public void changeEmail(int userId, String newEmail) {
        try (Connection connection = ConnectionManager.get()) {
            Optional<Error> emailNotValidError = userValidator.checkEmail(newEmail);
            if (emailNotValidError.isPresent())
                throw new ValidationException(Collections.singletonList(emailNotValidError.get()));

            User user = userDao.find(connection, userId)
                    .orElseThrow(() -> new DaoException("User with id " + userId + " not found"));
            user.setEmail(newEmail);
            userDao.update(connection, userId, user);
        } catch (SQLException | DaoException e) {
            log.error("Email change failed", e);
        }
    }

    @Override
    public void changePassword(int userId, String newPassword) {
        try (Connection connection = ConnectionManager.get()) {
            Optional<Error> passwordNotValidError = userValidator.checkPassword(newPassword);
            if (passwordNotValidError.isPresent())
                throw new ValidationException(Collections.singletonList(passwordNotValidError.get()));

            String encryptedPassword = passwordEncryptor.encrypt(newPassword);
            User user = userDao.find(connection, userId)
                    .orElseThrow(() -> new DaoException("User with id " + userId + " not found"));
            user.setPassword(encryptedPassword);
            userDao.update(connection, userId, user);
        } catch (SQLException | DaoException e) {
            log.error("Password change failed", e);
        }
    }

    @Override
    public void changeRole(int userId, Role role) {
        try (Connection connection = ConnectionManager.get()) {
            User user = userDao.find(connection, userId)
                    .orElseThrow(() -> new DaoException("User with id " + userId + " not found"));
            user.setRole(role);
            userDao.update(connection, userId, user);
        } catch (SQLException | DaoException e) {
            log.error("Role changing failed", e);
        }
    }

    @Override
    public void upgradeToPremium(int userId) {
        try (Connection connection = ConnectionManager.get()) {
            User user = userDao.find(connection, userId)
                    .orElseThrow(() -> new DaoException("User with id " + userId + " not found"));
            user.setRole(Role.USER_PR);
            userDao.update(connection, userId, user);
        } catch (SQLException | DaoException e) {
            log.error("Upgrade to premium failed", e);
        }
    }

    @Override
    public void ban(int userId) {
        try (Connection connection = ConnectionManager.get()) {
            User user = userDao.find(connection, userId)
                    .orElseThrow(() -> new DaoException("User with id " + userId + " not found"));
            user.setRole(Role.BANNED);
            userDao.update(connection, userId, user);
        } catch (SQLException | DaoException e) {
            log.error("Ban failed", e);
        }
    }


    private void checkCredentialsOrThrowException(String email, String password) {
        List<Error> errors = new ArrayList<>();
        userValidator.checkEmail(email).ifPresent(errors::add);
        userValidator.checkPassword(password).ifPresent(errors::add);

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
