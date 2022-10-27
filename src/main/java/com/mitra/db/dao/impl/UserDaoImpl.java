package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.QueryExecutor;
import com.mitra.db.Table;
import com.mitra.db.dao.UserDao;
import com.mitra.db.filter.Filter;
import com.mitra.db.mapper.UserRowMapper;
import com.mitra.entity.User;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    private UserDaoImpl(){}

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final QueryExecutor<Integer, User> queryExecutor = new QueryExecutor<>(UserRowMapper.getInstance());

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s, %s, %s FROM %s JOIN %s ON %s = %s"
            , Column.USER.id, Column.USER.email, Column.USER.password, Column.ROLE.role,
            Table.USER, Table.ROLE, Column.USER.role_id, Column.ROLE.id);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.USER.id);

    public static final String FIND_BY_EMAIL_AND_PASSWORD = FIND_ALL_SQL + String.format(" WHERE %s = ? AND %s = ?",
            Column.USER.email, Column.USER.password);

    public static final String SAVE_SQL = String.format(
            "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, (SELECT %s FROM %s WHERE %s = ?))",
            Table.USER, Column.USER.email.shortName(), Column.USER.password.shortName(), Column.USER.role_id.shortName(),
            Column.ROLE.id, Table.ROLE, Column.ROLE.role);

    public static final String UPDATE_SQL = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = (SELECT %s FROM %s WHERE %s = ?) WHERE %s = ?",
            Table.USER, Column.USER.email.shortName(), Column.USER.password.shortName(), Column.USER.role_id.shortName(),
            Column.ROLE.id, Table.ROLE, Column.ROLE.role, Column.USER.id);

    public static final String DELETE_SQL = String.format(
            "DELETE FROM %s WHERE %s = ?",
            Table.USER, Column.USER.id);

    @Override
    public Optional<User> find(Connection connection, Integer id) throws DaoException {
        return queryExecutor.find(connection, FIND_SQL, id);
    }

    @Override
    public Optional<User> find(Connection connection, String email, String password) throws DaoException {
        return queryExecutor.find(connection, FIND_BY_EMAIL_AND_PASSWORD, email, password);
    }

    @Override
    public List<User> findAll(Connection connection, Filter filter) throws DaoException {
        return queryExecutor.findAll(connection, FIND_ALL_SQL);
    }

    @Override
    public Integer save(Connection connection, User entity) throws DaoException {
        return queryExecutor.save(connection, SAVE_SQL, entity.getEmail(), entity.getPassword(), entity.getRole().name());
    }

    @Override
    public void update(Connection connection, Integer id, User entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL, entity.getEmail(), entity.getPassword(), entity.getRole().name(), id);
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }
}
