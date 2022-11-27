package com.mitra.db.mapper;

import com.mitra.entity.Role;
import com.mitra.entity.impl.UserImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserImpl> {

    @Override
    public UserImpl map(ResultSet resultSet) throws SQLException {
        return UserImpl.builder()
                .id(resultSet.getInt(1))
                .email(resultSet.getString(2))
                .password(resultSet.getString(3))
                .role(Role.valueOf(resultSet.getString(4)))
                .build();
    }
}
