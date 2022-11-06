package com.mitra.service;

import com.mitra.service.impl.LocationServiceImpl;
import com.mitra.service.impl.ProfileServiceImpl;
import com.mitra.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private UserService userService;
    private ProfileService profileService;
    private LocationService locationService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
        profileService = new ProfileServiceImpl();
        locationService = new LocationServiceImpl();
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
}
