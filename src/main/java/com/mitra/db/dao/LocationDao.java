package com.mitra.db.dao;

import com.mitra.entity.Location;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.Optional;

public interface LocationDao extends Dao<Integer, Location> {

    /**
     * Find Location in DB by city
     *
     * @param connection connection to db
     * @param city       city name
     * @return Optional of City
     */
    Optional<Location> findByCity(Connection connection, String city) throws DaoException;

}
