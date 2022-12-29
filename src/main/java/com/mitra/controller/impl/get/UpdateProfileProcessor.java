package com.mitra.controller.impl.get;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.entity.Gender;
import com.mitra.exception.NothingFoundException;
import com.mitra.service.InstrumentService;
import com.mitra.service.LocationService;
import com.mitra.service.ProfileService;
import com.mitra.service.SpecialityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateProfileProcessor implements GetController {

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
        int myId = SessionAttrAccessor.getProfileId(request);

        ProfileDto profile = profileService.find(myId)
                .orElseThrow(() -> new NothingFoundException("Profile with this id not found"));
        request.setAttribute("profile", profile);

        List<Gender> otherGenders = new ArrayList<>();
        Collections.addAll(otherGenders, Gender.values());
        otherGenders.remove(profile.getGender());
        request.setAttribute("otherGenders", otherGenders);

        List<LocationDto> otherLocations = locationService.getAll();
        otherLocations.remove(profile.getLocation());
        request.setAttribute("otherLocations", otherLocations);

        List<InstrumentDto> otherInstruments = instrumentService.getAll();
        otherInstruments.removeAll(profile.getInstruments());
        request.setAttribute("otherInstruments", otherInstruments);

        List<SpecialityDto> otherSpecialities = specialityService.getAll();
        otherSpecialities.removeAll(profile.getSpecialities());
        request.setAttribute("otherSpecialities", otherSpecialities);

        ControllerUtils.forward(request, response, GetUrl.UPDATE_PROFILE.getJspFileName());
    }
}
