package com.mitra.db.mapper;

import com.mitra.entity.Profile;
import com.mitra.entity.impl.*;

public class RowMapperFactory {

    private static final RowMapperFactory INSTANCE = new RowMapperFactory();

    private RowMapper<UserImpl> userRowMapper;
    private RowMapper<Profile> profileRowMapper;
    private RowMapper<LocationImpl> locationRowMapper;
    private RowMapper<Instrument> instrumentRowMapper;
    private RowMapper<SpecialityImpl> specialityRowMapper;
    private RowMapper<Like> likeRowMapper;

    private RowMapperFactory() {
        userRowMapper = new UserRowMapper();
        profileRowMapper = new ProfileRowMapper();
        locationRowMapper = new LocationRowMapper();
        instrumentRowMapper = new InstrumentRowMapper();
        specialityRowMapper = new SpecialityRowMapper();
        likeRowMapper = new LikeRowMapper();
    }

    public static RowMapperFactory getInstance() {
        return INSTANCE;
    }

    public RowMapper<UserImpl> getUserRowMapper() {
        return userRowMapper;
    }

    public RowMapper<Profile> getProfileRowMapper() {
        return profileRowMapper;
    }

    public RowMapper<LocationImpl> getLocationRowMapper() {
        return locationRowMapper;
    }

    public RowMapper<Instrument> getInstrumentRowMapper() {
        return instrumentRowMapper;
    }

    public RowMapper<SpecialityImpl> getSpecialityRowMapper() {
        return specialityRowMapper;
    }

    public RowMapper<Like> getLikeRowMapper() {
        return likeRowMapper;
    }
}
