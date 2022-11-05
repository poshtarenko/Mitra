package com.mitra.service;

import com.mitra.service.impl.LocationServiceImpl;
import com.mitra.service.impl.ProfileServiceImpl;
import com.mitra.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static ServiceFactory INSTANCE;

    private final UserService userService;
    private final ProfileService profileService;
    private final LocationService locationService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
        profileService = new ProfileServiceImpl();
        locationService = new LocationServiceImpl();
    }

    public static ServiceFactory getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ServiceFactory();
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
}
