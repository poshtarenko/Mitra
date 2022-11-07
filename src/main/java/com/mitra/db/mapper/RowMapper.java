package com.mitra.db.mapper;

import com.mitra.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<E extends Identifiable> {

    E map(ResultSet resultSet) throws SQLException;

}
