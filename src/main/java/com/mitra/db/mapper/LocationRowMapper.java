package com.mitra.db.mapper;

import com.mitra.entity.impl.LocationImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<LocationImpl> {

    @Override
    public LocationImpl map(ResultSet resultSet) throws SQLException {
        return LocationImpl.builder()
                .city(resultSet.getString(1))
                .localArea(resultSet.getString(2))
                .region(resultSet.getString(3))
                .country(resultSet.getString(4))
                .build();
    }
}
