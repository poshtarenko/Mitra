package com.mitra.db.dao;

import com.mitra.entity.Identifiable;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface Dao<K extends Number, E extends Identifiable> {

    /**
     * Execute select of 1 row of db entity table
     *
     * @param connection Connection to db
     * @param id         id of entity
     * @return found entity, mapped from db row to object
     * @throws DaoException if something bad
     */
    Optional<E> find(Connection connection, K id) throws DaoException;

    /**
     * Execute select of part of db entity table
     *
     * @param connection Connection to db
     * @return list of found mapped entities
     * @throws DaoException if something bad
     */
    List<E> findAll(Connection connection) throws DaoException;

    /**
     * Execute insert of 1 row to db table
     *
     * @param connection Connection to db
     * @param entity     Entity to save
     * @throws DaoException if something bad
     */
    K save(Connection connection, E entity) throws DaoException;

    /**
     * Execute update of 1 row to in table
     *
     * @param connection Connection to db
     * @param id         id in db
     * @param entity     object to save
     * @throws DaoException if something bad
     */
    void update(Connection connection, K id, E entity) throws DaoException;

    /**
     * Execute delete of 1 row to in table
     *
     * @param connection Connection to db
     * @param id         id in db
     * @throws DaoException if something bad
     */
    void delete(Connection connection, K id) throws DaoException;
}
