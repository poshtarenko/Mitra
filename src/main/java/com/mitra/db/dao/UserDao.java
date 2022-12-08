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

    /**
     * Execute update of password
     *
     * @param connection  Connection to db
     * @param userId      id
     * @param newPassword password
     * @throws DaoException if something bad
     */
    void changePassword(Connection connection, int userId, String newPassword) throws DaoException;

    /**
     * Execute update of role
     *
     * @param connection Connection to db
     * @param userId     user id
     * @param newRole    new role
     * @throws DaoException if something bad
     */
    void changeRole(Connection connection, int userId, Role newRole) throws DaoException;

}
