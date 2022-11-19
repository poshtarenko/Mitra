package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.UserDao;
import com.mitra.dto.UserDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Profile;
import com.mitra.entity.Role;
import com.mitra.entity.User;
import com.mitra.exception.ValidationException;
import com.mitra.security.EncryptorSHA512;
import com.mitra.security.PasswordEncryptor;
import com.mitra.service.UserService;
import com.mitra.validator.Validator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

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
    public Optional<UserDto> tryLogin(UserDto userDto) {
        try (Connection connection = ConnectionManager.get()) {
            checkUserDtoIsValid(userDto); //validation in order not to make redundant requests to DB

            String encryptedPassword = encryptPassword(userDto.getPassword());

            Optional<User> user = userDao.find(connection, userDto.getEmail(), encryptedPassword);

            return user.map(userDtoMapper::mapToDto);
        } catch (SQLException e) {
            // TODO : log
            return Optional.empty();
        }
    }

    @Override
    public boolean register(UserDto userDto) {
        try (Connection connection = ConnectionManager.get()) {
            checkUserDtoIsValid(userDto);

            User user = userDtoMapper.mapToEntity(userDto);
            String encryptedPassword = encryptPassword(userDto.getPassword());
            user.setPassword(encryptedPassword);

            userDao.save(connection, user);

            return true;
        } catch (SQLException e) {
            // TODO : log
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
        if (!userDtoValidator.isValid(userDto)){
            throw new ValidationException(userDto + " is invalid.");
        }
    }

    private String encryptPassword(String password){
        return passwordEncryptor.encrypt(password);
    }
}
