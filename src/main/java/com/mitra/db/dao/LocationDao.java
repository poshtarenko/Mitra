package com.mitra.db.dao;

import com.mitra.db.filter.Filter;
import com.mitra.entity.Location;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface LocationDao extends Dao<Integer, Location>{

    List<Location> findAll(Connection connection) throws DaoException;

    Optional<Location> findByCity(Connection connection, String city) throws DaoException;

}
