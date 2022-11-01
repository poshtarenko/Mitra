package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.QueryExecutor;
import com.mitra.db.dao.UserDao;
import com.mitra.db.filter.Filter;
import com.mitra.db.mapper.RowMapper;
import com.mitra.db.mapper.UserRowMapper;
import com.mitra.entity.Gender;
import com.mitra.entity.Profile;
import com.mitra.entity.User;
import com.mitra.entity.Location;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final UserDaoImpl INSTANCE = new UserDaoImpl();
    private static final ProfileDaoImpl profileDao = ProfileDaoImpl.getInstance();
    private static final LocationDaoImpl locationDao = LocationDaoImpl.getInstance();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final RowMapper<User> userRowMapper = UserRowMapper.getInstance();
    private static final QueryExecutor<Integer, User> queryExecutor = new QueryExecutor<>(userRowMapper);

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s, %s, %s FROM %s JOIN %s ON %s = %s",
            Column.USER.ID, Column.USER.EMAIL, Column.USER.PASSWORD, Column.ROLE.ROLE,
            Table.USER, Table.ROLE, Column.USER.ROLE_ID, Column.ROLE.ID);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.USER.ID);

    public static final String FIND_BY_EMAIL_AND_PASSWORD = FIND_ALL_SQL + String.format(" WHERE %s = ? AND %s = ?",
            Column.USER.EMAIL, Column.USER.PASSWORD);

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
        return queryExecutor.find(connection, FIND_SQL, id);
    }

    @Override
    public Optional<User> find(Connection connection, String email, String password) throws DaoException {
        return queryExecutor.find(connection, FIND_BY_EMAIL_AND_PASSWORD,
                email, password);
    }

    @Override
    public List<User> findAll(Connection connection, Filter filter) throws DaoException {
        return queryExecutor.findAll(connection, FIND_ALL_SQL);
    }

    @Override
    public Integer save(Connection connection, User entity) throws DaoException {
        Integer userId = queryExecutor.save(connection, SAVE_SQL,
                entity.getEmail(), entity.getPassword(), entity.getRole().name());

        // By default, user have location at Kyiv (city.id = 1)
        Location userLocation = locationDao.find(connection, 1)
                .orElseThrow(() -> new DaoException("There must be any city with id = 1"));

        Profile userProfile = Profile.builder()
                .id(userId)
                .name("UNDEFINED")
                .age(0)
                .gender(Gender.NEED_INIT)
                .text("UNDEFINED")
                .location(userLocation)
                .build();

        // When creating a user, an empty user profile must be created
        profileDao.save(connection, userProfile);

        return userId;
    }

    @Override
    public void update(Connection connection, Integer id, User entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL,
                entity.getEmail(), entity.getPassword(), entity.getRole().name(), id);
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        profileDao.delete(connection, id);
        queryExecutor.update(connection, DELETE_SQL, id);
    }
}
