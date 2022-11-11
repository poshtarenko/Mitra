package com.mitra.db.mapper;

import com.mitra.entity.Instrument;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InstrumentRowMapper implements RowMapper<Instrument> {
    @Override
    public Instrument map(ResultSet resultSet) throws SQLException {
        return new Instrument(
                resultSet.getInt(1),
                resultSet.getString(2)
        );
    }
}
