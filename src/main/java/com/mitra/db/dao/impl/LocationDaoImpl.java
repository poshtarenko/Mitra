package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.LocationDao;
import com.mitra.db.dao.impl.util.QueryExecutor;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Location;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class LocationDaoImpl implements LocationDao {

    private final RowMapper<Location> locationRowMapper;
    private final QueryExecutor<Integer, Location> queryExecutor;

    public LocationDaoImpl(RowMapper<Location> locationRowMapper) {
        this.locationRowMapper = locationRowMapper;
        this.queryExecutor = new QueryExecutor<>(locationRowMapper);
    }

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s, %s, %s FROM %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s",
            Column.CITY.NAME, Column.LOCAL_AREA.NAME, Column.REGION.NAME, Column.COUNTRY.NAME, Table.CITY,
            Table.LOCAL_AREA, Column.LOCAL_AREA.ID, Column.CITY.LOCAL_AREA_ID,
            Table.REGION, Column.REGION.ID, Column.LOCAL_AREA.ID,
            Table.COUNTRY, Column.COUNTRY.ID, Column.REGION.COUNTRY_ID);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.CITY.ID);

    public static final String FIND_BY_CITY_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.CITY.NAME);

    public static final String SAVE_SQL = null;

    public static final String UPDATE_SQL = null;

    public static final String DELETE_SQL = null;

    @Override
    public Optional<Location> find(Connection connection, Integer id) throws DaoException {
        return queryExecutor.selectOne(connection, FIND_SQL, id);
    }

    @Override
    public Optional<Location> findByCity(Connection connection, String city) throws DaoException {
        return queryExecutor.selectOne(connection, FIND_BY_CITY_SQL, city);
    }

    @Override
    public List<Location> findAll(Connection connection) throws DaoException {
        return queryExecutor.selectMany(connection, FIND_ALL_SQL);
    }

    @Override
    public Integer save(Connection connection, Location entity) throws DaoException {
        return queryExecutor.insert(connection, SAVE_SQL,
                entity.getCity(), entity.getLocalArea());
    }

    @Override
    public void update(Connection connection, Integer id, Location entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL,
                entity.getCity(), entity.getLocalArea(), id);
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }
}
