package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.ProfileDao;
import com.mitra.db.dao.UserDao;
import com.mitra.db.dao.impl.util.QueryExecutor;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.User;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final ProfileDao profileDao;
    private final QueryExecutor<Integer, User> queryExecutor;

    public UserDaoImpl(ProfileDao profileDao, RowMapper<User> userRowMapper) {
        this.profileDao = profileDao;
        this.queryExecutor = new QueryExecutor<>(userRowMapper);
    }

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s, %s, %s FROM %s JOIN %s ON %s = %s ",
            Column.USER.ID, Column.USER.EMAIL, Column.USER.PASSWORD, Column.ROLE.ROLE,
            Table.USER, Table.ROLE, Column.USER.ROLE_ID, Column.ROLE.ID);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.USER.ID);

    public static final String FIND_BY_EMAIL_AND_PASSWORD = FIND_ALL_SQL + String.format("WHERE %s = ? AND %s = ?",
            Column.USER.EMAIL, Column.USER.PASSWORD);

    public static final String FIND_BY_EMAIL = FIND_ALL_SQL + String.format("WHERE %s = ?", Column.USER.EMAIL);

    public static final String SAVE_SQL = String.format(
            "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, (SELECT %s FROM %s WHERE %s = ?))",
            Table.USER, Column.USER.EMAIL.shortName(), Column.USER.PASSWORD.shortName(), Column.USER.ROLE_ID.shortName(),
            Column.ROLE.ID, Table.ROLE, Column.ROLE.ROLE);

    public static final String UPDATE_SQL = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = (SELECT %s FROM %s WHERE %s = ?) WHERE %s = ?",
            Table.USER, Column.USER.EMAIL.shortName(), Column.USER.PASSWORD.shortName(), Column.USER.ROLE_ID.shortName(),
            Column.ROLE.ID, Table.ROLE, Column.ROLE.ROLE, Column.USER.ID);

    public static final String DELETE_SQL = String.format(
            "DELETE FROM %s WHERE %s = ?",
            Table.USER, Column.USER.ID);

    @Override
    public Optional<User> find(Connection connection, Integer id) throws DaoException {
        Optional<User> user = queryExecutor.selectOne(connection, FIND_SQL, id);
        user.ifPresent(value -> profileDao.find(connection, value.getId()).ifPresent(value::setProfile));
        return user;
    }

    @Override
    public Optional<User> findByEmail(Connection connection, String email) throws DaoException {
        Optional<User> user = queryExecutor.selectOne(connection, FIND_BY_EMAIL, email);
        user.ifPresent(value -> profileDao.find(connection, value.getId()).ifPresent(value::setProfile));
        return user;
    }

    @Override
    public Optional<User> findByEmail(Connection connection, String email, String password) throws DaoException {
        Optional<User> user = queryExecutor.selectOne(connection, FIND_BY_EMAIL_AND_PASSWORD, email, password);
        user.ifPresent(value -> profileDao.find(connection, value.getId()).ifPresent(value::setProfile));
        return user;
    }

    @Override
    public List<User> findAll(Connection connection) throws DaoException {
        return queryExecutor.selectMany(connection, FIND_ALL_SQL);
    }

    @Override
    public Integer save(Connection connection, User entity) throws DaoException {
        return queryExecutor.insert(connection, SAVE_SQL,
                entity.getEmail(), entity.getPassword(), entity.getRole().name());
    }

    @Override
    public void update(Connection connection, Integer id, User entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL,
                entity.getEmail(), entity.getPassword(), entity.getRole().name(), id);
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }
}
