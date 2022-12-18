package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.LocationHelper;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.entity.Gender;
import com.mitra.exception.ValidationException;
import com.mitra.service.InstrumentService;
import com.mitra.service.LocationService;
import com.mitra.service.ProfileService;
import com.mitra.service.SpecialityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UpdateProfileProcessor extends AbstractRequestProcessor {

    private final ProfileService profileService;
    private final LocationService locationService;
    private final InstrumentService instrumentService;
    private final SpecialityService specialityService;

    public UpdateProfileProcessor(ProfileService profileService, LocationService locationService,
                                  InstrumentService instrumentService, SpecialityService specialityService) {
        this.profileService = profileService;
        this.locationService = locationService;
        this.instrumentService = instrumentService;
        this.specialityService = specialityService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());
        Optional<ProfileDto> profileOptional = profileService.find(myId);

        if (!profileOptional.isPresent()) {
            throw new RuntimeException("Profile not found");
        }
        ProfileDto profile = profileOptional.get();
        String profileLocation = LocationHelper.locationDtoToString(profile.getLocation());
        String profileGender = profile.getGender().name();
        List<String> profileInstruments = profile.getInstruments().stream()
                .map(InstrumentDto::getName)
                .collect(Collectors.toList());
        List<String> profileSpecialities = profile.getSpecialities().stream()
                .map(SpecialityDto::getName)
                .collect(Collectors.toList());

        request.setAttribute("profile", profile);
        request.setAttribute("profileGender", profileGender);
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

        List<String> otherInstruments = instrumentService.getAll().stream()
                .map(InstrumentDto::getName)
                .collect(Collectors.toList());
        otherInstruments.removeAll(profileInstruments);
        request.setAttribute("otherInstruments", otherInstruments);

        List<String> otherSpecialities = specialityService.getAll().stream()
                .map(SpecialityDto::getName)
                .collect(Collectors.toList());
        otherSpecialities.removeAll(profileSpecialities);
        request.setAttribute("otherSpecialities", otherSpecialities);

        forward(request, response, AppUrl.UPDATE_PROFILE.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());

        List<InstrumentDto> instruments;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("instruments")))
            instruments = Arrays.stream(request.getParameterValues("instruments"))
                    .map(val -> new InstrumentDto(0, val))
                    .collect(Collectors.toList());
        else instruments = Collections.emptyList();

        List<SpecialityDto> specialities;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("specialities")))
            specialities = Arrays.stream(request.getParameterValues("specialities"))
                    .map(val -> new SpecialityDto(0, val))
                    .collect(Collectors.toList());
        else specialities = Collections.emptyList();

        Part photoPart = request.getPart("photo");
        InputStream photoInputStream = null;
        if (photoPart != null && photoPart.getSize() > 0)
            photoInputStream = photoPart.getInputStream();


        ProfileDto profileDto = ProfileDto.builder()
                .id(myId)
                .name(request.getParameter("name"))
                .age(Integer.valueOf(request.getParameter("age")))
                .gender(Gender.valueOf(request.getParameter("gender")))
                .text(request.getParameter("text"))
                .location(LocationHelper.stringToLocationDto(request.getParameter("location")))
                .instruments(instruments)
                .specialities(specialities)
                .photoPath(request.getParameter("photoPath"))
                .build();

        try {
            profileService.updateProfile(myId, profileDto, photoInputStream);
        } catch (ValidationException e) {
            request.setAttribute("errors", e.getErrors());
            processGet(request, response);
            return;
        }

        redirect(response, AppUrl.MY_PROFILE.getUrl());
    }
}
