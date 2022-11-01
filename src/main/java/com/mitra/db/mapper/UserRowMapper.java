package com.mitra.db.mapper;

import com.mitra.db.dao.ProfileDao;
import com.mitra.db.dao.impl.ProfileDaoImpl;
import com.mitra.entity.Profile;
import com.mitra.entity.Role;
import com.mitra.entity.User;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRowMapper implements RowMapper<User> {

    private static final UserRowMapper INSTANCE = new UserRowMapper();
    private static final ProfileDao profileDao = ProfileDaoImpl.getInstance();

    private UserRowMapper(){}

    public static UserRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User map(Connection connection, ResultSet resultSet) throws SQLException {
        Integer userId = resultSet.getInt(1);
        Profile profile = profileDao.find(connection, userId)
                .orElseThrow(() -> new DaoException("User without profile is impossible"));

        return User.builder()
                .id(userId)
                .email(resultSet.getString(2))
                .password(resultSet.getString(3))
                .role(Role.valueOf(resultSet.getString(4)))
                .profile(profile)
                .build();
    }
}
