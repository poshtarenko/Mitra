package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.util.LocationHelper;
import com.mitra.controller.request_processor.util.ProfileHelper;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Gender;
import com.mitra.service.*;

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
    private final SpecialityService specialityService = serviceFactory.getSpecialityService();

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
        List<String> profileInstruments = instrumentService.getProfileInstruments(user.getId()).stream()
                .map(InstrumentDto::getName)
                .collect(Collectors.toList());
        List<String> profileSpecialities = specialityService.getProfileSpecialities(user.getId()).stream()
                .map(SpecialityDto::getName)
                .collect(Collectors.toList());

        request.setAttribute("profileName", profile.getName());
        request.setAttribute("profileAge", profile.getAge());
        request.setAttribute("profileGender", profileGender);
        request.setAttribute("profileText", profile.getText());
        request.setAttribute("profileLocation", profileLocation);
        request.setAttribute("profileInstruments", profileInstruments);
        request.setAttribute("profileSpecialities", profileSpecialities);

        List<String> otherGenders = Arrays.stream(Gender.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        otherGenders.remove(profileGender);
        request.setAttribute("otherGenders", otherGenders);

        List<String> otherLocations = locationService.getAll().stream()
                .map(LocationHelper::locationDtoToString)
                .collect(Collectors.toList());
        otherLocations.remove(profileLocation);
        request.setAttribute("otherLocations", otherLocations);

        List<String> otherInstruments = ProfileHelper.getInstrumentsAsStrings(instrumentService);
        otherInstruments.removeAll(profileInstruments);
        request.setAttribute("otherInstruments", otherInstruments);

        List<String> otherSpecialities = ProfileHelper.getSpecialitiesAsStrings(specialityService);
        otherSpecialities.removeAll(profileSpecialities);
        request.setAttribute("otherSpecialities", otherSpecialities);

        forward(request, response, UrlPath.UPDATE_PROFILE.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = ((UserDto) request.getSession().getAttribute(SessionAttributes.USER.name())).getId();

        List<InstrumentDto> instruments;
        if (request.getParameterValues("instruments") != null)
            instruments = Arrays.stream(request.getParameterValues("instruments"))
                    .map(val -> InstrumentDto.builder().id(0).name(val).build())
                    .collect(Collectors.toList());
        else instruments = Collections.emptyList();

        List<SpecialityDto> specialities;
        if (request.getParameterValues("specialities") != null)
            specialities = Arrays.stream(request.getParameterValues("specialities"))
                    .map(val -> SpecialityDto.builder().id(0).name(val).build())
                    .collect(Collectors.toList());
        else specialities = Collections.emptyList();

        ProfileDto profileDto = ProfileDto.builder()
                .name(request.getParameter("name"))
                .age(Integer.valueOf(request.getParameter("age")))
                .gender(Gender.valueOf(request.getParameter("gender")))
                .text(request.getParameter("text"))
                .location(LocationHelper.stringToLocationDto(request.getParameter("location")))
                .instruments(instruments)
                .specialities(specialities)
                .build();

        profileService.updateProfile(userId, profileDto);

        redirect(response, UrlPath.UPDATE_PROFILE.get());
    }
}
