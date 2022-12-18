package com.mitra.db.mapper.impl;

import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Location;
import com.mitra.entity.impl.LocationImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<Location> {

    @Override
    public Location map(ResultSet resultSet) throws SQLException {
        return LocationImpl.builder()
                .id(resultSet.getInt(1))
                .city(resultSet.getString(2))
                .localArea(resultSet.getString(3))
                .region(resultSet.getString(4))
                .country(resultSet.getString(5))
                .build();
    }
}
