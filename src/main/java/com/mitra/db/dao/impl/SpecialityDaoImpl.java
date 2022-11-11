package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.QueryExecutor;
import com.mitra.db.dao.SpecialityDao;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Speciality;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpecialityDaoImpl implements SpecialityDao {

    private final RowMapper<Speciality> specialityRowMapper;
    private final QueryExecutor<Integer, Speciality> queryExecutor;

    public SpecialityDaoImpl(RowMapper<Speciality> specialityRowMapper) {
        this.specialityRowMapper = specialityRowMapper;
        this.queryExecutor = new QueryExecutor<>(specialityRowMapper);
    }

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s FROM %s ",
            Column.SPECIALITY.ID, Column.SPECIALITY.NAME, Table.SPECIALITY);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.SPECIALITY.ID);

    public static final String FIND_ALL_SPECIALITIES_BY_PROFILE_ID = String.format(
            "SELECT %s, %s FROM %s JOIN %s ON %s = %s WHERE %s = ?",
            Column.SPECIALITY.ID, Column.SPECIALITY.NAME, Table.SPECIALITY,
            Table.PROFILE_SPECIALITY, Column.PROFILE_SPECIALITY.SPECIALITY_ID, Column.SPECIALITY.ID,
            Column.PROFILE_SPECIALITY.PROFILE_ID);

    public static final String SET_SPECIALITIES_TO_PROFILE = String.format(
            "INSERT INTO %s (SELECT ?, %s FROM %s WHERE %s IN (#L))",
            Table.PROFILE_SPECIALITY, Column.SPECIALITY.ID, Table.SPECIALITY, Column.SPECIALITY.NAME);

    public static final String DELETE_ALL_PROFILE_SPECIALITIES = String.format(
            "DELETE FROM %s WHERE %s = ?",
            Table.PROFILE_SPECIALITY, Column.PROFILE_SPECIALITY.PROFILE_ID);

    public static final String SAVE_SQL = null;

    public static final String UPDATE_SQL = null;

    public static final String DELETE_SQL = null;

    @Override
    public Optional<Speciality> find(Connection connection, Integer id) throws DaoException {
        return queryExecutor.find(connection, FIND_SQL, id);
    }

    @Override
    public List<Speciality> findAll(Connection connection) throws DaoException {
        return queryExecutor.findAll(connection, FIND_ALL_SQL);
    }

    @Override
    public Integer save(Connection connection, Speciality entity) throws DaoException {
        return queryExecutor.save(connection, SAVE_SQL);
    }

    @Override
    public void update(Connection connection, Integer id, Speciality entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL, id);
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }

    @Override
    public List<Speciality> getProfileSpecialities(Connection connection, int profileId) {
        return queryExecutor.findAll(connection, FIND_ALL_SPECIALITIES_BY_PROFILE_ID, profileId);
    }

    @Override
    public void setProfileSpecialities(Connection connection, int profileId, List<Speciality> specialities) {
        deleteAllProfileSpecialities(connection, profileId);

        if (specialities.size() > 0) {
            String SQL = SET_SPECIALITIES_TO_PROFILE.replace("#L",
                    specialities.stream().map(v -> "?").collect(Collectors.joining(", ")));

            List<String> specialityNames = specialities.stream().map(Speciality::getName).collect(Collectors.toList());
            queryExecutor.update(connection, SQL, profileId, specialityNames);
        }
    }

    @Override
    public void deleteAllProfileSpecialities(Connection connection, int profileId) {
        queryExecutor.update(connection, DELETE_ALL_PROFILE_SPECIALITIES, profileId);
    }
}
