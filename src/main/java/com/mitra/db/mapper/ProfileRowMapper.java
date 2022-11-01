package com.mitra.db.mapper;

import com.mitra.entity.Gender;
import com.mitra.entity.Profile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRowMapper implements RowMapper<Profile> {
    private static final ProfileRowMapper INSTANCE = new ProfileRowMapper();

    private ProfileRowMapper() {
    }

    public static ProfileRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Profile map(Connection connection, ResultSet resultSet) throws SQLException {
        return Profile.builder()
                .id(resultSet.getInt(1))
                .name(resultSet.getString(2))
                .age(resultSet.getInt(3))
                .gender(Gender.valueOf(resultSet.getString(4)))
                .text(resultSet.getString(5))
                .build();
    }
}
