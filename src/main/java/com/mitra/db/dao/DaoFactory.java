package com.mitra.db.dao;

import com.mitra.db.dao.impl.*;
import com.mitra.db.mapper.RowMapperFactory;

public class DaoFactory {

    private UserDao userDao;
    private ProfileDao profileDao;
    private LocationDao locationDao;
    private InstrumentDao instrumentDao;
    private SpecialityDao specialityDao;

    private static final DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {
        RowMapperFactory rowMapperFactory = RowMapperFactory.getInstance();

        locationDao = new LocationDaoImpl(rowMapperFactory.getLocationRowMapper());
        instrumentDao = new InstrumentDaoImpl(rowMapperFactory.getInstrumentRowMapper());
        specialityDao = new SpecialityDaoImpl(rowMapperFactory.getSpecialityRowMapper());
        profileDao = new ProfileDaoImpl(rowMapperFactory.getProfileRowMapper(), instrumentDao, specialityDao);
        userDao = new UserDaoImpl(profileDao, locationDao, RowMapperFactory.getInstance().getUserRowMapper());
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ProfileDao getProfileDao() {
        return profileDao;
    }

    public LocationDao getLocationDao() {
        return locationDao;
    }

    public InstrumentDao getInstrumentDao() {
        return instrumentDao;
    }

    public SpecialityDao getSpecialityDao() {
        return specialityDao;
    }
}
