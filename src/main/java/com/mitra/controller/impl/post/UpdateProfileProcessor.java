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
import com.mitra.service.ProfileService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UpdateProfileProcessor implements PostController {

    private final ProfileService profileService;

    public UpdateProfileProcessor(ProfileService profileService) {
        this.profileService = profileService;

    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        ProfileDto profile = createProfile(request);

        try {
            profileService.updateProfile(myId, profile);
            SessionAttrAccessor.updateProfile(request, profile);
        } catch (ValidationException e) {
            log.info("Trying to update profile with invalid fields : {}. {}", profile, e.getErrors());
            throw e;
        }

        response.sendRedirect(GetUrl.MY_PROFILE.getUrl());
    }

    private ProfileDto createProfile(HttpServletRequest request) {
        List<InstrumentDto> instruments = Collections.emptyList();
        if (ParameterHelper.parameterNotEmpty(request.getParameter("instruments"))) {
            instruments = Arrays.stream(request.getParameterValues("instruments"))
                    .map(val -> new InstrumentDto(Integer.parseInt(val), null))
                    .collect(Collectors.toList());
        }

        List<SpecialityDto> specialities = Collections.emptyList();
        if (ParameterHelper.parameterNotEmpty(request.getParameter("specialities"))) {
            specialities = Arrays.stream(request.getParameterValues("specialities"))
                    .map(val -> new SpecialityDto(Integer.parseInt(val), null))
                    .collect(Collectors.toList());
        }

        return ProfileDto.builder()
                .id(SessionAttrAccessor.getProfileId(request))
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
    }
}
