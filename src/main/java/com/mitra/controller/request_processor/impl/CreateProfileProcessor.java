package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.db.Column;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Gender;
import com.mitra.exception.ControllerException;
import com.mitra.exception.ServiceException;
import com.mitra.service.LocationService;
import com.mitra.service.ProfileService;
import com.mitra.service.impl.LocationServiceImpl;
import com.mitra.service.impl.ProfileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CreateProfileProcessor extends AbstractRequestProcessor {

    private static final LocationService locationService = LocationServiceImpl.getInstance();
    private static final ProfileService profileService = ProfileServiceImpl.getInstance();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cities", locationService.getAllCities());
        request.setAttribute("genders", Gender.values());

        forward(request, response, UrlPath.CREATE_PROFILE.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDto user = (UserDto) request.getSession().getAttribute(SessionAttributes.USER.name());

        ProfileDto profile = ProfileDto.builder()
                .name(request.getParameter("name"))
                .age(Integer.valueOf(request.getParameter("age")))
                .gender(Gender.valueOf(request.getParameter("gender")))
                .text(request.getParameter("text"))
                .location(request.getParameter("city"))
                .build();

        profileService.updateProfile(user.getId(), profile);

        redirect(response, UrlPath.SEARCH.get());
    }
}
