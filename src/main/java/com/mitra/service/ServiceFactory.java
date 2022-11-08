package com.mitra.service;

import com.mitra.db.dao.DaoFactory;
import com.mitra.dto.mapper.DtoMapperFactory;
import com.mitra.security.EncryptorSHA512;
import com.mitra.service.impl.InstrumentServiceImpl;
import com.mitra.service.impl.LocationServiceImpl;
import com.mitra.service.impl.ProfileServiceImpl;
import com.mitra.service.impl.UserServiceImpl;
import com.mitra.validator.ValidatorFactory;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private UserService userService;
    private ProfileService profileService;
    private LocationService locationService;
    private final InstrumentService instrumentService;

    private ServiceFactory() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        DtoMapperFactory dtoMapperFactory = DtoMapperFactory.getInstance();
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();

        userService = new UserServiceImpl(daoFactory.getUserDao(), dtoMapperFactory.getUserDtoMapper(),
                validatorFactory.getUserDtoValidator(), EncryptorSHA512.getInstance());
        profileService = new ProfileServiceImpl(daoFactory.getProfileDao(), dtoMapperFactory.getProfileDtoMapper());
        locationService = new LocationServiceImpl(daoFactory.getLocationDao(), dtoMapperFactory.getLocationDtoMapper());
        instrumentService = new InstrumentServiceImpl(daoFactory.getInstrumentDao(), dtoMapperFactory.getInstrumentDtoMapper());
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
}
