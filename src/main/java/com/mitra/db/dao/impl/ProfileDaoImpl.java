package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.InstrumentDao;
import com.mitra.db.dao.ProfileDao;
import com.mitra.db.dao.QueryExecutor;
import com.mitra.db.dao.SpecialityDao;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Profile;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileDaoImpl implements ProfileDao {

    private final RowMapper<Profile> profileRowMapper;
    private final QueryExecutor<Integer, Profile> queryExecutor;
    private final InstrumentDao instrumentDao;
    private final SpecialityDao specialityDao;

    public ProfileDaoImpl(RowMapper<Profile> profileRowMapper, InstrumentDao instrumentDao, SpecialityDao specialityDao) {
        this.profileRowMapper = profileRowMapper;
        this.instrumentDao = instrumentDao;
        this.specialityDao = specialityDao;
        this.queryExecutor = new QueryExecutor<>(profileRowMapper);
    }

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s " +
                    "JOIN %s ON %s = %s ",
            Column.PROFILE.ID, Column.PROFILE.NAME, Column.PROFILE.AGE, Column.GENDER.GENDER, Column.PROFILE.TEXT, Column.PROFILE.PHOTO_PATH,
            Column.CITY.NAME, Column.LOCAL_AREA.NAME, Column.REGION.NAME, Column.COUNTRY.NAME,
            Table.PROFILE,
            Table.GENDER, Column.PROFILE.GENDER_ID, Column.GENDER.ID,
            Table.CITY, Column.PROFILE.CITY_ID, Column.CITY.ID,
            Table.LOCAL_AREA, Column.CITY.LOCAL_AREA_ID, Column.LOCAL_AREA.ID,
            Table.REGION, Column.LOCAL_AREA.REGION_ID, Column.REGION.ID,
            Table.COUNTRY, Column.REGION.COUNTRY_ID, Column.COUNTRY.ID);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format(" WHERE %s = ?", Column.PROFILE.ID);

    public static final String SAVE_SQL = String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, (SELECT %s FROM %s WHERE %s = ?), ?, ?, (SELECT %s FROM %s WHERE %s = ?))",
            Table.PROFILE,
            Column.PROFILE.ID.shortName(), Column.PROFILE.NAME.shortName(), Column.PROFILE.AGE.shortName(),
            Column.PROFILE.GENDER_ID.shortName(), Column.PROFILE.TEXT.shortName(), Column.PROFILE.PHOTO_PATH.shortName(), Column.PROFILE.CITY_ID.shortName(),
            Column.GENDER.ID, Table.GENDER, Column.GENDER.GENDER,
            Column.CITY.ID, Table.CITY, Column.CITY.NAME);

    public static final String UPDATE_SQL = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = (SELECT %s FROM %s WHERE %s = ?), %s = ?, %s = ?,  %s = (SELECT %s FROM %s WHERE %s = ?) WHERE %s = ?",
            Table.PROFILE,
            Column.PROFILE.NAME.shortName(), Column.PROFILE.AGE.shortName(), Column.PROFILE.GENDER_ID.shortName(),
            Column.GENDER.ID, Table.GENDER, Column.GENDER.GENDER,
            Column.PROFILE.TEXT.shortName(), Column.PROFILE.PHOTO_PATH.shortName(), Column.PROFILE.CITY_ID.shortName(),
            Column.CITY.ID, Table.CITY, Column.CITY.NAME,
            Column.PROFILE.ID.shortName());

    public static final String DELETE_SQL = String.format(
            "DELETE FROM %s WHERE %s = ?",
            Table.PROFILE, Column.PROFILE.ID);

    public static final String GET_ALL_IDS = String.format(
            "SELECT %s FROM %s",
            Column.USER.ID, Table.USER);

    @Override
    public Optional<Profile> find(Connection connection, Integer id) throws DaoException {
        Optional<Profile> profile = queryExecutor.find(connection, FIND_SQL, id);
        if (profile.isPresent()) {
            profile.get().setInstruments(instrumentDao.getProfileInstruments(connection, id));
            profile.get().setSpecialities(specialityDao.getProfileSpecialities(connection, id));
        }
        return profile;
    }

    @Override
    public List<Profile> findAll(Connection connection) throws DaoException {
        List<Profile> profiles = queryExecutor.findAll(connection, FIND_ALL_SQL);
        profiles.forEach(profile -> {
            profile.setInstruments(instrumentDao.getProfileInstruments(connection, profile.getId()));
            profile.setSpecialities(specialityDao.getProfileSpecialities(connection, profile.getId()));
        });
        return profiles;
    }

    @Override
    public List<Integer> getAllIds(Connection connection) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_IDS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> ids = new ArrayList<>();
            while (resultSet.next())
                ids.add(resultSet.getInt(1));
            return ids;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer save(Connection connection, Profile entity) throws DaoException {
        return queryExecutor.save(connection, SAVE_SQL,
                entity.getId(), entity.getName(), entity.getAge(), entity.getGender().name(),
                entity.getText(), entity.getPhotoPath(), entity.getLocation().getCity());
    }

    @Override
    public void update(Connection connection, Integer id, Profile entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL,
                entity.getName(), entity.getAge(), entity.getGender().name(), entity.getText(),
                entity.getPhotoPath(), entity.getLocation().getCity(), id);
        instrumentDao.setProfileInstruments(connection, id, entity.getInstruments());
        specialityDao.setProfileSpecialities(connection, id, entity.getSpecialities());
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }
}
