package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;
import com.mitra.service.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyProfileProcessor extends AbstractRequestProcessor {

    private final ProfileService profileService;

    public MyProfileProcessor(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());

        ProfileDto profile = profileService.find(myId)
                .orElseThrow(() -> new ServletException("User without profile must be impossible, but it does not"));
        request.setAttribute("profile", profile);
        forward(request, response, UrlPath.MY_PROFILE.getJspFileName());
    }
}
