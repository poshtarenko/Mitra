package com.mitra.db.mapper.impl;

import com.mitra.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdRowMapper implements RowMapper<Long> {
    @Override
    public Long map(ResultSet resultSet) throws SQLException {
        return resultSet.getLong(1);
    }
}
