package com.mitra.controller.request_processor.impl;

import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.db.filter.ProfileFilter;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.entity.Gender;
import com.mitra.entity.impl.InstrumentImpl;
import com.mitra.entity.impl.SpecialityImpl;
import com.mitra.service.InstrumentService;
import com.mitra.service.LocationService;
import com.mitra.service.ProfileService;
import com.mitra.service.SpecialityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchProcessor extends AbstractRequestProcessor {

    private final LocationService locationService;
    private final InstrumentService instrumentService;
    private final SpecialityService specialityService;
    private final ProfileService profileService;

    public SearchProcessor(LocationService locationService, InstrumentService instrumentService,
                           SpecialityService specialityService, ProfileService profileService) {
        this.locationService = locationService;
        this.instrumentService = instrumentService;
        this.specialityService = specialityService;
        this.profileService = profileService;
    }

    private static final int PAGE_SIZE = 5;

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int page = 1;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("page")))
            page = Integer.parseInt(request.getParameter("page"));

        String name = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("name"))) {
            name = request.getParameter("name");
            request.setAttribute("selectedName", name);
        }

        Gender gender = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("gender"))) {
            gender = Gender.valueOf(request.getParameter("gender"));
            request.setAttribute("selectedGender", gender);
        }

        Integer minAge = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("minAge"))) {
            minAge = Integer.parseInt(request.getParameter("minAge"));
            request.setAttribute("selectedMinAge", minAge);
        }

        Integer maxAge = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("maxAge"))) {
            maxAge = Integer.parseInt(request.getParameter("maxAge"));
            request.setAttribute("selectedMaxAge", maxAge);
        }

        String city = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("city"))) {
            city = request.getParameter("city");
            request.setAttribute("selectedCity", city);
        }

        String localArea = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("localArea"))) {
            localArea = request.getParameter("localArea");
            request.setAttribute("selectedLocalArea", localArea);
        }

        String region = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("region"))) {
            region = request.getParameter("region");
            request.setAttribute("selectedRegion", region);
        }

        List<String> specialities = new ArrayList<>();
        if (ParameterHelper.parameterNotEmpty(request.getParameter("specialities"))) {
            specialities = Arrays.asList(request.getParameterValues("specialities"));
            request.setAttribute("selectedSpecialities", specialities);
        }

        List<String> instruments = new ArrayList<>();
        if (ParameterHelper.parameterNotEmpty(request.getParameter("instruments"))) {
            instruments = Arrays.asList(request.getParameterValues("instruments"));
            request.setAttribute("selectedInstruments", instruments);
        }

        ProfileFilter profileFilter = ProfileFilter.builder()
                .name(name)
                .gender(gender)
                .minAge(minAge)
                .maxAge(maxAge)
                .city(city)
                .localArea(localArea)
                .region(region)
                .instruments(instruments.stream().
                        map(val -> new InstrumentImpl(0, val))
                        .collect(Collectors.toList()))
                .specialities(specialities.stream().
                        map(val -> new SpecialityImpl(0, val))
                        .collect(Collectors.toList()))
                .build();

        request.setAttribute("profiles", profileService
                .getAll(
                        profileFilter,
                        PAGE_SIZE,
                        PAGE_SIZE * (page - 1))
        );

        List<Integer> pages = new ArrayList<>();
        int pageCount = (int) Math.ceil(profileService.getCount(profileFilter) / (double) PAGE_SIZE);
        for (int pageNum = 1; pageNum <= pageCount; pageNum++) {
            pages.add(pageNum);
        }
        request.setAttribute("pages", pages);

        List<String> genders = Arrays.stream(Gender.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        genders.remove(gender);
        request.setAttribute("genders", genders);

        List<String> cities = locationService.getAll().stream()
                .map(LocationDto::getCity)
                .collect(Collectors.toList());
        cities.remove(city);
        request.setAttribute("cities", cities);

        Set<String> localAreas = locationService.getAll().stream()
                .map(LocationDto::getLocalArea)
                .collect(Collectors.toSet());
        localAreas.remove(localArea);
        request.setAttribute("localAreas", localAreas);

        Set<String> regions = locationService.getAll().stream()
                .map(LocationDto::getRegion)
                .collect(Collectors.toSet());
        regions.remove(region);
        request.setAttribute("regions", regions);

        List<String> instrumentsToJSP = instrumentService.getAll().stream()
                .map(InstrumentDto::getName)
                .collect(Collectors.toList());
        instrumentsToJSP.removeAll(instruments);
        request.setAttribute("instruments", instrumentsToJSP);

        List<String> specialitiesToJSP = specialityService.getAll().stream()
                .map(SpecialityDto::getName)
                .collect(Collectors.toList());
        specialitiesToJSP.removeAll(specialities);
        request.setAttribute("specialities", specialitiesToJSP);

        forward(request, response, AppUrl.SEARCH.getJspFileName());
    }
}
