package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.dto.ProfileDto;
import com.mitra.exception.NothingFoundException;
import com.mitra.service.ProfileService;
import com.mitra.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyProfileProcessor extends AbstractRequestProcessor {

    private final ProfileService profileService;
    private final TrackService trackService;

    public MyProfileProcessor(ProfileService profileService, TrackService trackService) {
        this.profileService = profileService;
        this.trackService = trackService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());

        ProfileDto profile = profileService.find(myId)
                .orElseThrow(() -> new NothingFoundException("Profile not found"));
        request.setAttribute("profile", profile);
        request.setAttribute("tracks", trackService.getProfileMusic(myId));
        forward(request, response, AppUrl.MY_PROFILE.getJspFileName());
    }
}
