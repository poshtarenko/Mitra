package com.mitra.db.dao;

import com.mitra.db.dao.impl.LocationDaoImpl;
import com.mitra.db.dao.impl.ProfileDaoImpl;
import com.mitra.db.dao.impl.UserDaoImpl;
import com.mitra.db.mapper.RowMapperFactory;

public class DaoFactory {

    private UserDao userDao;
    private ProfileDao profileDao;
    private LocationDao locationDao;

    private static final DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {
        profileDao = new ProfileDaoImpl();
        locationDao = new LocationDaoImpl();
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
}
