package com.mitra.db.mapper;

import com.mitra.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<E extends Identifiable> {

    /**
     * Do mapping from rows in DB to Java-POJO
     *
     * @param resultSet resultSet with relevant rows
     * @return E object
     */
    E map(ResultSet resultSet) throws SQLException;

}
