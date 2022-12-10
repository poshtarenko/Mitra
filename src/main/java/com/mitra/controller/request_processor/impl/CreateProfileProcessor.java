package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.util.LocationHelper;
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
import java.util.List;
import java.util.stream.Collectors;

public class CreateProfileProcessor extends AbstractRequestProcessor {

    private final LocationService locationService;
    private final ProfileService profileService;

    public CreateProfileProcessor(LocationService locationService, ProfileService profileService) {
        this.locationService = locationService;
        this.profileService = profileService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> locations = locationService.getAll().stream()
                .map(LocationHelper::locationDtoToString)
                .collect(Collectors.toList());
        request.setAttribute("cities", locations);
        request.setAttribute("genders", Gender.values());

        forward(request, response, UrlPath.CREATE_PROFILE.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());

        System.out.println(request.getParameter("name"));

        LocationDto locationDto = LocationHelper.stringToLocationDto(request.getParameter("city"));

        ProfileDto profile = ProfileDto.builder()
                .id(myId)
                .name(request.getParameter("name"))
                .age(Integer.valueOf(request.getParameter("age")))
                .gender(Gender.valueOf(request.getParameter("gender")))
                .text(request.getParameter("text"))
                .location(locationDto)
                .build();

        try {
            profileService.create(profile);
            request.getSession().setAttribute(SessionAttributes.USER_NAME.name(), profile.getName());
        } catch (ValidationException e) {
            request.setAttribute("errors", e.getErrors());
            processGet(request, response);
        }

        redirect(response, UrlPath.MY_PROFILE.getUrl());
    }

}
