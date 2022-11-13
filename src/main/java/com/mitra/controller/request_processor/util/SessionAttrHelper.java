package com.mitra.controller.request_processor.util;

import com.mitra.controller.SessionAttributes;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public final class SessionAttrHelper {

    public static void getUserWithUpdatedProfile(HttpServletRequest request, ProfileDto profile) {
        UserDto user = (UserDto) request.getSession().getAttribute(SessionAttributes.USER.name());

        UserDto updatedUser = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .profile(profile)
                .build();

        request.getSession().setAttribute(SessionAttributes.USER.name(), updatedUser);
    }


}
