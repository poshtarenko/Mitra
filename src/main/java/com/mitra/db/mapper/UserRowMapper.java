package com.mitra.db.mapper;

import com.mitra.entity.Role;
import com.mitra.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User map(Connection connection, ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt(1))
                .email(resultSet.getString(2))
                .password(resultSet.getString(3))
                .role(Role.valueOf(resultSet.getString(4)))
                .build();
    }
}
