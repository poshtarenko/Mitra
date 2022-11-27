package com.mitra.db.mapper;

import com.mitra.db.mapper.impl.*;
import com.mitra.entity.*;

public class RowMapperFactory {

    private static final RowMapperFactory INSTANCE = new RowMapperFactory();

    private final RowMapper<User> userRowMapper;
    private final RowMapper<Profile> profileRowMapper;
    private final RowMapper<Track> musicRowMapper;
    private final RowMapper<Location> locationRowMapper;
    private final RowMapper<Instrument> instrumentRowMapper;
    private final RowMapper<Speciality> specialityRowMapper;
    private final RowMapper<Like> likeRowMapper;

    private RowMapperFactory() {
        userRowMapper = new UserRowMapper();
        profileRowMapper = new ProfileRowMapper();
        musicRowMapper = new MusicRowMapper();
        locationRowMapper = new LocationRowMapper();
        instrumentRowMapper = new InstrumentRowMapper();
        specialityRowMapper = new SpecialityRowMapper();
        likeRowMapper = new LikeRowMapper();
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

    public RowMapper<Like> getLikeRowMapper() {
        return likeRowMapper;
    }

    public RowMapper<Track> getMusicRowMapper() {
        return musicRowMapper;
    }
}
