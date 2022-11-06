package com.mitra.db.mapper;

import com.mitra.entity.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<Location> {

    @Override
    public Location map(Connection connection, ResultSet resultSet) throws SQLException {
        return Location.builder()
                .city(resultSet.getString(1))
                .localArea(resultSet.getString(2))
                .region(resultSet.getString(3))
                .country(resultSet.getString(4))
                .build();
    }
}
