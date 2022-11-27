package com.mitra.db.mapper.impl;

import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Speciality;
import com.mitra.entity.impl.SpecialityImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialityRowMapper implements RowMapper<Speciality> {
    @Override
    public Speciality map(ResultSet resultSet) throws SQLException {
        return new SpecialityImpl(
                resultSet.getInt(1),
                resultSet.getString(2)
        );
    }
}
