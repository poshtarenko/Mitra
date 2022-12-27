package com.mitra.db.dao;

import com.mitra.db.dao.impl.*;
import com.mitra.db.mapper.RowMapperFactory;

public class DaoFactory {

    private final UserDao userDao;
    private final ProfileDao profileDao;
    private final TrackDao trackDao;
    private final LocationDao locationDao;
    private final InstrumentDao instrumentDao;
    private final SpecialityDao specialityDao;
    private final LikeDao likeDao;
    private final ChatDao chatDao;
    private final MessageDao messageDao;

    private static final DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {
        RowMapperFactory rowMapperFactory = RowMapperFactory.getInstance();

        messageDao = new MessageDaoImpl(rowMapperFactory.getMessageRowMapper());
        chatDao = new ChatDaoImpl(rowMapperFactory.getChatRowMapper(), messageDao);
        likeDao = new LikeDaoImpl(rowMapperFactory.getLikeRowMapper());
        trackDao = new TrackDaoImpl(rowMapperFactory.getMusicRowMapper());
        locationDao = new LocationDaoImpl(rowMapperFactory.getLocationRowMapper());
        instrumentDao = new InstrumentDaoImpl(rowMapperFactory.getInstrumentRowMapper());
        specialityDao = new SpecialityDaoImpl(rowMapperFactory.getSpecialityRowMapper());
        profileDao = new ProfileDaoImpl(rowMapperFactory.getProfileRowMapper(), instrumentDao,
                specialityDao, trackDao, likeDao);
        userDao = new UserDaoImpl(profileDao, rowMapperFactory.getUserRowMapper());
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

    public LikeDao getLikeDao() {
        return likeDao;
    }

    public TrackDao getTrackDao() {
        return trackDao;
    }

    public ChatDao getChatDao() {
        return chatDao;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }
}
