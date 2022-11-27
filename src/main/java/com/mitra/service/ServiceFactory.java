package com.mitra.service;

import com.mitra.cloud.CloudStorageProviderImpl;
import com.mitra.cloud.GoogleDriveInitializer;
import com.mitra.db.dao.DaoFactory;
import com.mitra.dto.mapper.DtoMapperFactory;
import com.mitra.security.EncryptorSHA512;
import com.mitra.service.impl.*;
import com.mitra.validator.ValidatorFactory;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService;
    private final ProfileService profileService;
    private final TrackService trackService;
    private final ProfileLikeService profileLikeService;
    private final LocationService locationService;
    private final InstrumentService instrumentService;
    private final SpecialityService specialityService;

    private ServiceFactory() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        DtoMapperFactory dtoMapperFactory = DtoMapperFactory.getInstance();
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        CloudStorageProviderImpl cloudStorageProvider = new CloudStorageProviderImpl(GoogleDriveInitializer.getDriveService());

        locationService = new LocationServiceImpl(daoFactory.getLocationDao(), dtoMapperFactory.getLocationDtoMapper());
        instrumentService = new InstrumentServiceImpl(daoFactory.getInstrumentDao(), dtoMapperFactory.getInstrumentDtoMapper());
        specialityService = new SpecialityServiceImpl(daoFactory.getSpecialityDao(), dtoMapperFactory.getSpecialityDtoMapper());
        profileLikeService = new ProfileLikeServiceImpl(daoFactory.getLikeDao(), dtoMapperFactory.getLikeDtoMapper());
        userService = new UserServiceImpl(daoFactory.getUserDao(), dtoMapperFactory.getUserDtoMapper(),
                validatorFactory.getUserDtoValidator(), EncryptorSHA512.getInstance());
        trackService = new TrackServiceImpl(dtoMapperFactory.getTrackDtoMapper(), daoFactory.getProfileDao(),
                daoFactory.getTrackDao(), cloudStorageProvider);
        profileService = new ProfileServiceImpl(daoFactory.getProfileDao(), dtoMapperFactory.getProfileDtoMapper(),
                cloudStorageProvider);
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public InstrumentService getInstrumentService() {
        return instrumentService;
    }

    public SpecialityService getSpecialityService() {
        return specialityService;
    }

    public ProfileLikeService getProfileLikeService() {
        return profileLikeService;
    }

    public TrackService getTrackService() {
        return trackService;
    }
}
