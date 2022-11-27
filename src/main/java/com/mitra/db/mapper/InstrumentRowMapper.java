package com.mitra.db.mapper;

import com.mitra.entity.impl.Instrument;
import com.mitra.entity.impl.InstrumentImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InstrumentRowMapper implements RowMapper<Instrument> {
    @Override
    public Instrument map(ResultSet resultSet) throws SQLException {
        return new InstrumentImpl(
                resultSet.getInt(1),
                resultSet.getString(2)
        );
    }
}
