package com.mitra.db.dao;

import com.mitra.entity.impl.UserImpl;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.Optional;

public interface UserDao extends Dao<Integer, UserImpl> {
    /**
     * Execute select of 1 row of db entity table
     *
     * @param connection Connection to db
     * @param email      email
     * @param password   password
     * @return found entity, mapped from db row to object
     * @throws DaoException if something bad
     */
    Optional<UserImpl> find(Connection connection, String email, String password) throws DaoException;

}
