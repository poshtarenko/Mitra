package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.service.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetPreviewTrackController implements PostController {

    private final ProfileService profileService;

    public SetPreviewTrackController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        int trackId = ParameterHelper.getNecessaryIntParameter(request, "trackId");

        profileService.setPreviewTrack(myId, trackId);

        response.sendRedirect(GetUrl.MUSIC.getUrl());
    }
}
