package com.mitra.controller.request_processor.impl;

import com.mitra.controller.AppUrl;
import com.mitra.controller.SessionAttributes;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Gender;
import com.mitra.exception.ValidationException;
import com.mitra.service.LocationService;
import com.mitra.service.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateProfileProcessor extends AbstractRequestProcessor {

    private final LocationService locationService;
    private final ProfileService profileService;

    public CreateProfileProcessor(LocationService locationService, ProfileService profileService) {
        this.locationService = locationService;
        this.profileService = profileService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cities", locationService.getAll());
        request.setAttribute("genders", Gender.values());

        forward(request, response, AppUrl.CREATE_PROFILE.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());

        LocationDto location = LocationDto.builder()
                .id(Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "location")))
                .build();

        ProfileDto profile = ProfileDto.builder()
                .id(myId)
                .name(ParameterHelper.getNecessaryParameter(request, "name"))
                .age(Integer.valueOf(ParameterHelper.getNecessaryParameter(request, "age")))
                .gender(Gender.valueOf(ParameterHelper.getNecessaryParameter(request, "gender")))
                .text(ParameterHelper.getNecessaryParameter(request, "text"))
                .location(location)
                .build();

        try {
            profileService.create(profile);
            request.getSession().setAttribute(SessionAttributes.USER_NAME.name(), profile.getName());
        } catch (ValidationException e) {
            request.setAttribute("errors", e.getErrors());
            processGet(request, response);
        }

        redirect(response, AppUrl.MY_PROFILE.getUrl());
    }

}
