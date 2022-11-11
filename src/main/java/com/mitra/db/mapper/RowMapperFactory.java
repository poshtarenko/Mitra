package com.mitra.db.mapper;

import com.mitra.entity.*;

public class RowMapperFactory {

    private static final RowMapperFactory INSTANCE = new RowMapperFactory();

    private RowMapper<User> userRowMapper;
    private RowMapper<Profile> profileRowMapper;
    private RowMapper<Location> locationRowMapper;
    private RowMapper<Instrument> instrumentRowMapper;
    private RowMapper<Speciality> specialityRowMapper;

    private RowMapperFactory() {
        userRowMapper = new UserRowMapper();
        profileRowMapper = new ProfileRowMapper();
        locationRowMapper = new LocationRowMapper();
        instrumentRowMapper = new InstrumentRowMapper();
        specialityRowMapper = new SpecialityRowMapper();
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

    public RowMapper<Instrument> getInstrumentRowMapper() {
        return instrumentRowMapper;
    }

    public RowMapper<Speciality> getSpecialityRowMapper() {
        return specialityRowMapper;
    }
}
