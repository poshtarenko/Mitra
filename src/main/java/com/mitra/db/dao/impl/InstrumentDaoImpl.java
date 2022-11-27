package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.InstrumentDao;
import com.mitra.db.dao.QueryExecutor;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Instrument;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InstrumentDaoImpl implements InstrumentDao {

    private final RowMapper<Instrument> instrumentRowMapper;
    private final QueryExecutor<Integer, Instrument> queryExecutor;

    public InstrumentDaoImpl(RowMapper<Instrument> locationRowMapper) {
        this.instrumentRowMapper = locationRowMapper;
        this.queryExecutor = new QueryExecutor<>(locationRowMapper);
    }

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s FROM %s ",
            Column.INSTRUMENT.ID, Column.INSTRUMENT.NAME, Table.INSTRUMENT);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.INSTRUMENT.ID);

    public static final String FIND_ALL_INSTRUMENTS_BY_PROFILE_ID = String.format(
            "SELECT %s, %s FROM %s JOIN %s ON %s = %s WHERE %s = ?",
            Column.INSTRUMENT.ID, Column.INSTRUMENT.NAME, Table.INSTRUMENT,
            Table.PROFILE_INSTRUMENT, Column.PROFILE_INSTRUMENT.INSTRUMENT_ID, Column.INSTRUMENT.ID,
            Column.PROFILE_INSTRUMENT.PROFILE_ID);

    // tip : INSERT INTO profile_instrument (SELECT 15, instrument.id FROM instrument WHERE name IN ('Guitar', 'Drums'));
    public static final String SET_INSTRUMENTS_TO_PROFILE = String.format(
            "INSERT INTO %s (SELECT ?, %s FROM %s WHERE %s IN (#L))",
            Table.PROFILE_INSTRUMENT, Column.INSTRUMENT.ID, Table.INSTRUMENT, Column.INSTRUMENT.NAME);

    public static final String DELETE_ALL_PROFILE_INSTRUMENTS = String.format(
            "DELETE FROM %s WHERE %s = ?",
            Table.PROFILE_INSTRUMENT, Column.PROFILE_INSTRUMENT.PROFILE_ID);

    public static final String SAVE_SQL = null;

    public static final String UPDATE_SQL = null;

    public static final String DELETE_SQL = null;

    @Override
    public Optional<Instrument> find(Connection connection, Integer id) throws DaoException {
        return queryExecutor.find(connection, FIND_SQL, id);
    }

    @Override
    public List<Instrument> findAll(Connection connection) throws DaoException {
        return queryExecutor.findAll(connection, FIND_ALL_SQL);
    }

    @Override
    public Integer save(Connection connection, Instrument entity) throws DaoException {
        return queryExecutor.save(connection, SAVE_SQL);
    }

    @Override
    public void update(Connection connection, Integer id, Instrument entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL, id);
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }

    @Override
    public List<Instrument> getProfileInstruments(Connection connection, int profileId) {
        return queryExecutor.findAll(connection, FIND_ALL_INSTRUMENTS_BY_PROFILE_ID, profileId);
    }

    @Override
    public void setProfileInstruments(Connection connection, int profileId, List<Instrument> instruments) {
        deleteAllProfileInstruments(connection, profileId);

        if (instruments.size() > 0) {
            String SQL = SET_INSTRUMENTS_TO_PROFILE.replace("#L",
                    instruments.stream().map(v -> "?").collect(Collectors.joining(", ")));

            List<String> instrumentNames = instruments.stream().map(Instrument::getName).collect(Collectors.toList());
            queryExecutor.update(connection, SQL, profileId, instrumentNames);
        }
    }

    @Override
    public void deleteAllProfileInstruments(Connection connection, int profileId) {
        queryExecutor.update(connection, DELETE_ALL_PROFILE_INSTRUMENTS, profileId);
    }
}
