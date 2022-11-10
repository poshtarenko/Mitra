package com.mitra.db.mapper;

import com.mitra.entity.Gender;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRowMapper implements RowMapper<Profile> {

    @Override
    public Profile map(ResultSet resultSet) throws SQLException {
        Location location = Location.builder()
                .city(resultSet.getString(7))
                .localArea(resultSet.getString(8))
                .region(resultSet.getString(9))
                .country(resultSet.getString(10))
                .build();

        return Profile.builder()
                .id(resultSet.getInt(1))
                .name(resultSet.getString(2))
                .age(resultSet.getInt(3))
                .gender(Gender.valueOf(resultSet.getString(4)))
                .text(resultSet.getString(5))
                .photoPath(resultSet.getString(6))
                .location(location)
                .build();
    }
}
