package com.mitra.service;

import com.mitra.service.impl.LocationServiceImpl;
import com.mitra.service.impl.ProfileServiceImpl;
import com.mitra.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final UserService userService;
    private static final ProfileService profileService;
    private static final LocationService locationService;

    static {
        userService = UserServiceImpl.getInstance();
        profileService = ProfileServiceImpl.getInstance();
        locationService = LocationServiceImpl.getInstance();
    }

    public static UserService getUserService() {
        return userService;
    }

    public static ProfileService getProfileService() {
        return profileService;
    }

    public static LocationService getLocationService() {
        return locationService;
    }
}
