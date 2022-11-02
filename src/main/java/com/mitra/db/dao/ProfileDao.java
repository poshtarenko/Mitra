package com.mitra.db.dao;

import com.mitra.entity.Profile;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface ProfileDao extends Dao<Integer, Profile> {

    List<Profile> findAll(Connection connection) throws DaoException;

}
