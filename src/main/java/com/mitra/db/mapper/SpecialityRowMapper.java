package com.mitra.db.mapper;

import com.mitra.entity.Speciality;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialityRowMapper implements RowMapper<Speciality> {
    @Override
    public Speciality map(ResultSet resultSet) throws SQLException {
        return new Speciality(
                resultSet.getInt(1),
                resultSet.getString(2)
        );
    }
}
