package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.util.LocationHelper;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Gender;
import com.mitra.entity.Profile;
import com.mitra.service.InstrumentService;
import com.mitra.service.LocationService;
import com.mitra.service.ProfileService;
import com.mitra.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UpdateProfileProcessor extends AbstractRequestProcessor {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ProfileService profileService = serviceFactory.getProfileService();
    private final LocationService locationService = serviceFactory.getLocationService();
    private final InstrumentService instrumentService = serviceFactory.getInstrumentService();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDto user = (UserDto) request.getSession().getAttribute(SessionAttributes.USER.name());
        Optional<ProfileDto> profileOptional = profileService.getByUserId(user.getId());

        if (!profileOptional.isPresent()){
            throw new RuntimeException("Profile not found");
        }
        ProfileDto profile = profileOptional.get();
        String profileLocation = LocationHelper.locationDtoToString(profile.getLocation());
        String profileGender = profile.getGender().name();
        Set<String> profileInstruments = instrumentService.getProfileInstruments(user.getId()).stream()
                .map(InstrumentDto::getName)
                .collect(Collectors.toSet());

        request.setAttribute("profileName", profile.getName());
        request.setAttribute("profileAge", profile.getAge());
        request.setAttribute("profileGender", profileGender);
        request.setAttribute("profileText", profile.getText());
        request.setAttribute("profileLocation", profileLocation);
        request.setAttribute("profileInstruments", profileInstruments);

        Set<String> otherGenders = Arrays.stream(Gender.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
        otherGenders.remove(profileGender);
        request.setAttribute("otherGenders", otherGenders);

        Set<String> otherLocations = locationService.getAll().stream()
                .map(LocationHelper::locationDtoToString)
                .collect(Collectors.toSet());
        otherLocations.remove(profileLocation);
        request.setAttribute("otherLocations", otherLocations);

        Set<String> otherInstruments = instrumentService.getAll().stream()
                .map(InstrumentDto::getName)
                .collect(Collectors.toSet());
        otherInstruments.removeAll(profileInstruments);
        request.setAttribute("otherInstruments", otherInstruments);

        forward(request, response, UrlPath.UPDATE_PROFILE.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = ((UserDto) request.getSession().getAttribute(SessionAttributes.USER.name())).getId();

        Set<String> instrumentsParams;
        if (request.getParameterValues("instruments") != null)
            instrumentsParams = new HashSet<>(Arrays.asList(request.getParameterValues("instruments")));
        else
            instrumentsParams = Collections.emptySet();

        List<InstrumentDto> instruments = instrumentsParams.stream()
                .map(val -> InstrumentDto.builder().id(0).name(val).build())
                .collect(Collectors.toList());

        ProfileDto profileDto = ProfileDto.builder()
                .name(request.getParameter("name"))
                .age(Integer.valueOf(request.getParameter("age")))
                .gender(Gender.valueOf(request.getParameter("gender")))
                .text(request.getParameter("text"))
                .location(LocationHelper.stringToLocationDto(request.getParameter("location")))
                .instruments(instruments)
                .build();

        profileService.updateProfile(userId, profileDto);

        redirect(response, UrlPath.UPDATE_PROFILE.get());
    }
}
