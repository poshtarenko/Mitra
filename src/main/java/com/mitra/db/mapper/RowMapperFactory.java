package com.mitra.db.mapper;

import com.mitra.db.dao.DaoFactory;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;
import com.mitra.entity.User;

public class RowMapperFactory {

    private static final RowMapperFactory INSTANCE = new RowMapperFactory();

    private RowMapper<User> userRowMapper;
    private RowMapper<Profile> profileRowMapper;
    private RowMapper<Location> locationRowMapper;

    private RowMapperFactory() {
        userRowMapper = new UserRowMapper();
        profileRowMapper = new ProfileRowMapper();
        locationRowMapper = new LocationRowMapper();
    }

    public static RowMapperFactory getInstance() {
        return INSTANCE;
    }

    public RowMapper<User> getUserRowMapper() {
        return userRowMapper;
    }

    public RowMapper<Profile> getProfileRowMapper() {
        return profileRowMapper;
    }

    public RowMapper<Location> getLocationRowMapper() {
        return locationRowMapper;
    }
}
