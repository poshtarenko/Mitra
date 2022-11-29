package com.mitra.controller.request_processor.impl;

import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.util.LocationHelper;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.db.filter.ProfileFilter;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.entity.Gender;
import com.mitra.entity.Instrument;
import com.mitra.entity.Speciality;
import com.mitra.entity.impl.InstrumentImpl;
import com.mitra.entity.impl.SpecialityImpl;
import com.mitra.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SearchProcessor extends AbstractRequestProcessor {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final LocationService locationService = serviceFactory.getLocationService();
    private final InstrumentService instrumentService = serviceFactory.getInstrumentService();
    private final SpecialityService specialityService = serviceFactory.getSpecialityService();
    private final ProfileService profileService = serviceFactory.getProfileService();


    private static final int PAGE_SIZE = 5;

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> cities = locationService.getAll().stream()
                .map(LocationDto::getCity)
                .collect(Collectors.toList());
        request.setAttribute("cities", cities);

        Set<String> localAreas = locationService.getAll().stream()
                .map(LocationDto::getLocalArea)
                .collect(Collectors.toSet());
        request.setAttribute("localAreas", localAreas);

        Set<String> regions = locationService.getAll().stream()
                .map(LocationDto::getRegion)
                .collect(Collectors.toSet());
        request.setAttribute("regions", regions);

        List<String> instrumentsToJSP = instrumentService.getAll().stream()
                .map(InstrumentDto::getName)
                .collect(Collectors.toList());
        request.setAttribute("instruments", instrumentsToJSP);

        List<String> specialitiesToJSP = specialityService.getAll().stream()
                .map(SpecialityDto::getName)
                .collect(Collectors.toList());
        request.setAttribute("specialities", specialitiesToJSP);

        int page = 1;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("page")))
            page = Integer.parseInt(request.getParameter("page"));

        String name = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("name")))
            name = request.getParameter("name");

        Gender gender = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("gender")))
            gender = Gender.valueOf(request.getParameter("gender"));

        Integer minAge = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("minAge")))
            minAge = Integer.parseInt(request.getParameter("minAge"));

        Integer maxAge = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("maxAge")))
            maxAge = Integer.parseInt(request.getParameter("maxAge"));

        String city = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("city")))
            city = request.getParameter("city");

        String localArea = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("localArea")))
            localArea = request.getParameter("localArea");

        String region = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("region")))
            region = request.getParameter("region");

        List<Speciality> specialities;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("specialities")))
            specialities = Arrays.stream(request.getParameterValues("specialities"))
                    .map(val -> new SpecialityImpl(0, val))
                    .collect(Collectors.toList());
        else specialities = Collections.emptyList();

        List<Instrument> instruments;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("instruments")))
            instruments = Arrays.stream(request.getParameterValues("instruments"))
                    .map(val -> new InstrumentImpl(0, val))
                    .collect(Collectors.toList());
        else instruments = Collections.emptyList();

        ProfileFilter profileFilter = ProfileFilter.builder()
                .name(name)
                .gender(gender)
                .minAge(minAge)
                .maxAge(maxAge)
                .city(city)
                .localArea(localArea)
                .region(region)
                .instruments(instruments)
                .specialities(specialities)
                .build();

        request.setAttribute("profiles", profileService
                .findAll(
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
        forward(request, response, UrlPath.SEARCH.getJspFileName());
    }
}
