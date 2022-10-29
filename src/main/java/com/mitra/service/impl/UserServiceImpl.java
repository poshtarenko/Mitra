package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.UserDao;
import com.mitra.db.dao.impl.UserDaoImpl;
import com.mitra.dto.UserDto;
import com.mitra.dto.mapper.UserDtoMapper;
import com.mitra.entity.Role;
import com.mitra.entity.User;
import com.mitra.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final UserServiceImpl INSTANCE = new UserServiceImpl();

    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final UserDtoMapper userDtoMapper = UserDtoMapper.getInstance();

    public UserServiceImpl(){}

    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<UserDto> tryLogin(String email, String password) {
        try (Connection connection = ConnectionManager.get()) {
            // TODO : validation (in order not to make redundant requests to DB)

            Optional<User> user = userDao.find(connection, email, password);

            return user.map(userDtoMapper::mapToDto);
        } catch (SQLException e) {
            // TODO : log
            return Optional.empty();
        }
    }

    @Override
    public boolean register(UserDto userDto) {
        try (Connection connection = ConnectionManager.get()) {
            // TODO : validation

            User user = userDtoMapper.mapToEntity(userDto);
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
}
