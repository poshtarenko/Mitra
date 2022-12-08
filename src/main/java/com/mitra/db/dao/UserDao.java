package com.mitra.db.dao;

import com.mitra.entity.Role;
import com.mitra.entity.User;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.Optional;

public interface UserDao extends Dao<Integer, User> {
    /**
     * Execute select of 1 row of db entity table
     *
     * @param connection Connection to db
     * @param email      email
     * @param password   password
     * @return found entity, mapped from db row to object
     * @throws DaoException if something bad
     */
    Optional<User> find(Connection connection, String email, String password) throws DaoException;

}
