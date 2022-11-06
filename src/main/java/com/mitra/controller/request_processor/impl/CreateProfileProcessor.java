package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Gender;
import com.mitra.service.LocationService;
import com.mitra.service.ProfileService;
import com.mitra.service.ServiceFactory;
import com.mitra.service.impl.LocationServiceImpl;
import com.mitra.service.impl.ProfileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CreateProfileProcessor extends AbstractRequestProcessor {

    private static final ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private static final LocationService locationService = serviceFactory.getLocationService();
    private static final ProfileService profileService = serviceFactory.getProfileService();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> locations = locationService.getAllCities().stream()
                .map(this::locationDtoToString)
                .collect(Collectors.toList());
        request.setAttribute("cities", locations);
        request.setAttribute("genders", Gender.values());

        forward(request, response, UrlPath.CREATE_PROFILE.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDto user = (UserDto) request.getSession().getAttribute(SessionAttributes.USER.name());

        System.out.println(request.getParameter("name"));

        LocationDto locationDto = stringToLocationDto(request.getParameter("city"));

        ProfileDto profile = ProfileDto.builder()
                .name(request.getParameter("name"))
                .age(Integer.valueOf(request.getParameter("age")))
                .gender(Gender.valueOf(request.getParameter("gender")))
                .text(request.getParameter("text"))
                .location(locationDto)
                .build();

        profileService.updateProfile(user.getId(), profile);

        redirect(response, UrlPath.SEARCH.get());
    }

    private String locationDtoToString(LocationDto locationDto) {
        StringBuilder stringBuilder = new StringBuilder();
        String delimiter = ", ";
        stringBuilder.append(locationDto.getCity())
                .append(delimiter)
                .append(locationDto.getLocalArea())
                .append(delimiter)
                .append(locationDto.getRegion())
                .append(delimiter)
                .append(locationDto.getCountry());
        return stringBuilder.toString();
    }

    private LocationDto stringToLocationDto(String string) {
        String[] split = string.split(", ");
        return LocationDto.builder()
                .city(split[0])
                .localArea(split[1])
                .region(split[2])
                .country(split[3])
                .build();
    }

}
