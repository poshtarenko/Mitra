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

    private UserService userService;
    private ProfileService profileService;
    private ProfileLikeService profileLikeService;
    private LocationService locationService;
    private InstrumentService instrumentService;
    private SpecialityService specialityService;

    private ServiceFactory() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        DtoMapperFactory dtoMapperFactory = DtoMapperFactory.getInstance();
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();

        locationService = new LocationServiceImpl(daoFactory.getLocationDao(), dtoMapperFactory.getLocationDtoMapper());
        instrumentService = new InstrumentServiceImpl(daoFactory.getInstrumentDao(), dtoMapperFactory.getInstrumentDtoMapper());
        specialityService = new SpecialityServiceImpl(daoFactory.getSpecialityDao(), dtoMapperFactory.getSpecialityDtoMapper());
        profileLikeService = new ProfileLikeServiceImpl(daoFactory.getLikeDao(), dtoMapperFactory.getLikeDtoMapper());
        userService = new UserServiceImpl(daoFactory.getUserDao(), dtoMapperFactory.getUserDtoMapper(),
                validatorFactory.getUserDtoValidator(), EncryptorSHA512.getInstance());
        profileService = new ProfileServiceImpl(daoFactory.getProfileDao(), dtoMapperFactory.getProfileDtoMapper(),
                new CloudStorageProviderImpl(GoogleDriveInitializer.getDriveService()));
        profileLikeService = new ProfileLikeServiceImpl(daoFactory.getLikeDao(), dtoMapperFactory.getLikeDtoMapper());
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

    public ProfileLikeService getProfileLikesService() {
        return profileLikeService;
    }
}
