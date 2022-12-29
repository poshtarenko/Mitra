package com.mitra.controller.impl.util;

import com.mitra.controller.SessionAttributes;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public final class SessionAttrAccessor {

    public static void dropAll(HttpServletRequest request) {
        request.getSession().setAttribute(SessionAttributes.USER.name(), null);
    }

    public static UserDto getUser(HttpServletRequest request) {
        return (UserDto) request.getSession()
                .getAttribute(SessionAttributes.USER.name());
    }

    public static void setUser(HttpServletRequest request, UserDto user) {
        request.getSession().setAttribute(SessionAttributes.USER.name(), user);
    }

    public static ProfileDto getProfile(HttpServletRequest request) {
        ProfileDto profile = null;

        UserDto user = (UserDto) request.getSession()
                .getAttribute(SessionAttributes.USER.name());
        if (user != null)
            profile = user.getProfile();

        return profile;
    }

    public static int getProfileId(HttpServletRequest request) {
        return getProfile(request).getId();
    }

    public static void updateProfile(HttpServletRequest request, ProfileDto profile) {
        UserDto user = getUser(request);
        if (user != null) {
            UserDto updatedUser = UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .profile(profile)
                    .build();
            setUser(request, updatedUser);
        }
    }

}
