package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.service.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class UpdateProfilePhotoController implements PostController {

    private final ProfileService profileService;

    public UpdateProfilePhotoController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        Part photoPart = ParameterHelper.getNecessaryPart(request, "photo");
        profileService.updatePhoto(myId, photoPart.getInputStream());
        response.sendRedirect(GetUrl.MY_PROFILE.getUrl());
    }
}
