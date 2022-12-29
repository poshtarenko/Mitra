package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.entity.Gender;
import com.mitra.exception.ValidationException;
import com.mitra.service.InstrumentService;
import com.mitra.service.LocationService;
import com.mitra.service.ProfileService;
import com.mitra.service.SpecialityService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UpdateProfileProcessor implements PostController {

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
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        List<InstrumentDto> instruments;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("instruments")))
            instruments = Arrays.stream(request.getParameterValues("instruments"))
                    .map(val -> new InstrumentDto(Integer.parseInt(val), null))
                    .collect(Collectors.toList());
        else instruments = Collections.emptyList();

        List<SpecialityDto> specialities;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("specialities")))
            specialities = Arrays.stream(request.getParameterValues("specialities"))
                    .map(val -> new SpecialityDto(Integer.parseInt(val), null))
                    .collect(Collectors.toList());
        else specialities = Collections.emptyList();

        ProfileDto profile = ProfileDto.builder()
                .id(myId)
                .name(request.getParameter("name"))
                .age(Integer.valueOf(request.getParameter("age")))
                .gender(Gender.getById(Integer.parseInt(request.getParameter("gender"))))
                .text(request.getParameter("text"))
                .location(LocationDto.builder()
                        .id(Integer.parseInt(request.getParameter("location")))
                        .build())
                .instruments(instruments)
                .specialities(specialities)
                .photoPath(request.getParameter("photoPath"))
                .build();

        try {
            profileService.updateProfile(myId, profile);
            SessionAttrAccessor.updateProfile(request, profile);
        } catch (ValidationException e) {
            log.info("Trying to update profile with invalid fields : {}. {}", profile, e.getErrors());
            throw e;
        }

        response.sendRedirect(GetUrl.MY_PROFILE.getUrl());
    }
}
