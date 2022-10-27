package com.mitra.db.mapper;

import com.mitra.entity.Identifiable;

import java.sql.Connection;
import java.sql.ResultSet;

public interface RowMapper<E extends Identifiable> {

    E map(Connection connection, ResultSet resultSet);

}
