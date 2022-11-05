package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.UserDao;
import com.mitra.db.dao.impl.UserDaoImpl;
import com.mitra.dto.UserDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.dto.mapper.UserDtoMapper;
import com.mitra.entity.Role;
import com.mitra.entity.User;
import com.mitra.exception.ValidationException;
import com.mitra.security.EncryptorSHA512;
import com.mitra.security.PasswordEncryptor;
import com.mitra.service.UserService;
import com.mitra.validator.UserDtoValidator;
import com.mitra.validator.Validator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final UserServiceImpl INSTANCE = new UserServiceImpl();

    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final DtoMapper<UserDto, User> userDtoMapper = UserDtoMapper.getInstance();
    private static final Validator<UserDto> userDtoValidator = UserDtoValidator.getInstance();
    private static final PasswordEncryptor passwordEncryptor = EncryptorSHA512.getInstance();

    private UserServiceImpl(){}

    public static UserServiceImpl getInstance() {
        return INSTANCE;
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

            User user = userDtoMapper.mapToEntity(connection, userDto);
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

    private static void checkUserDtoIsValid(UserDto userDto) {
        if (!userDtoValidator.isValid(userDto)){
            throw new ValidationException(userDto + " is invalid.");
        }
    }

    private static String encryptPassword(String password){
        return passwordEncryptor.encrypt(password);
    }
}
