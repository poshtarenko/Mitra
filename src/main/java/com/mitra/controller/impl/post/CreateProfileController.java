package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Gender;
import com.mitra.exception.ValidationException;
import com.mitra.service.ProfileService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CreateProfileController implements PostController {

    private final ProfileService profileService;

    public CreateProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        LocationDto location = LocationDto.builder()
                .id(Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "location")))
                .build();

        ProfileDto profile = ProfileDto.builder()
                .id(myId)
                .name(ParameterHelper.getNecessaryParameter(request, "name"))
                .age(Integer.valueOf(ParameterHelper.getNecessaryParameter(request, "age")))
                .gender(Gender.getById(Integer.parseInt(request.getParameter("gender"))))
                .text(ParameterHelper.getNecessaryParameter(request, "text"))
                .location(location)
                .build();

        try {
            profileService.create(profile);
            SessionAttrAccessor.updateProfile(request, profile);
        } catch (ValidationException e) {
            log.info("Trying to create profile with invalid fields : {}. {}", profile, e.getErrors());
            throw e;
        }

        response.sendRedirect(GetUrl.MY_PROFILE.getUrl());
    }

}
