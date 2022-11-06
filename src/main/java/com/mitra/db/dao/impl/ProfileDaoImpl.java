package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.ProfileDao;
import com.mitra.db.dao.QueryExecutor;
import com.mitra.db.filter.Filter;
import com.mitra.db.mapper.ProfileRowMapper;
import com.mitra.db.mapper.RowMapper;
import com.mitra.db.mapper.RowMapperFactory;
import com.mitra.entity.Profile;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ProfileDaoImpl implements ProfileDao {

    private RowMapper<Profile> profileRowMapper = RowMapperFactory.getInstance().getProfileRowMapper();
    private QueryExecutor<Integer, Profile> queryExecutor = new QueryExecutor<>(profileRowMapper);

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s ",
            Column.PROFILE.ID, Column.PROFILE.NAME, Column.PROFILE.AGE, Column.GENDER.GENDER, Column.PROFILE.TEXT,
            Column.CITY.NAME, Column.LOCAL_AREA.NAME, Column.REGION.NAME, Column.COUNTRY.NAME,
            Table.PROFILE,
            Table.GENDER, Column.PROFILE.GENDER_ID, Column.GENDER.ID,
            Table.CITY, Column.PROFILE.CITY_ID, Column.CITY.ID,
            Table.LOCAL_AREA, Column.CITY.LOCAL_AREA_ID, Column.LOCAL_AREA.ID,
            Table.REGION, Column.LOCAL_AREA.REGION_ID, Column.REGION.ID,
            Table.COUNTRY, Column.REGION.COUNTRY_ID, Column.COUNTRY.ID);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.PROFILE.ID);

    public static final String SAVE_SQL = String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, (SELECT %s FROM %s WHERE %s = ?), ?, (SELECT %s FROM %s WHERE %s = ?))",
            Table.PROFILE,
            Column.PROFILE.ID.shortName(), Column.PROFILE.NAME.shortName(), Column.PROFILE.AGE.shortName(),
            Column.PROFILE.GENDER_ID.shortName(), Column.PROFILE.TEXT.shortName(), Column.PROFILE.CITY_ID.shortName(),
            Column.GENDER.ID, Table.GENDER, Column.GENDER.GENDER,
            Column.CITY.ID, Table.CITY, Column.CITY.NAME);

    public static final String UPDATE_SQL = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = (SELECT %s FROM %s WHERE %s = ?), %s = ?,  %s = (SELECT %s FROM %s WHERE %s = ?) WHERE %s = ?",
            Table.PROFILE,
            Column.PROFILE.NAME.shortName(), Column.PROFILE.AGE.shortName(), Column.PROFILE.GENDER_ID.shortName(),
            Column.GENDER.ID, Table.GENDER, Column.GENDER.GENDER,
            Column.PROFILE.TEXT.shortName(), Column.PROFILE.CITY_ID.shortName(),
            Column.CITY.ID, Table.CITY, Column.CITY.NAME,
            Column.PROFILE.ID.shortName());

    public static final String DELETE_SQL = String.format(
            "DELETE FROM %s WHERE %s = ?",
            Table.PROFILE, Column.PROFILE.ID);

    @Override
    public Optional<Profile> find(Connection connection, Integer id) throws DaoException {
        return queryExecutor.find(connection, FIND_SQL, id);
    }

    @Override
    public List<Profile> findAll(Connection connection, Filter filter) throws DaoException {
        return queryExecutor.findAll(connection, FIND_ALL_SQL);
    }

    @Override
    public List<Profile> findAll(Connection connection) throws DaoException {
        return queryExecutor.findAll(connection, FIND_ALL_SQL);
    }

    @Override
    public Integer save(Connection connection, Profile entity) throws DaoException {
        return queryExecutor.save(connection, SAVE_SQL,
                entity.getId(), entity.getName(), entity.getAge(), entity.getGender().name(),
                entity.getText(), entity.getLocation().getCity());
    }

    @Override
    public void update(Connection connection, Integer id, Profile entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL,
                entity.getName(), entity.getAge(), entity.getGender().name(), entity.getText(),
                entity.getLocation().getCity(), id);
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }
}
