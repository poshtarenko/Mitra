package com.mitra.db.dao;

import com.mitra.entity.Profile;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface ProfileDao extends Dao<Integer, Profile> {

    /**
     * Get list of all profile IDs
     *
     * @param connection connection to db
     * @return list of all profile IDs
     */
    List<Integer> getAllIds(Connection connection) throws DaoException;
}
