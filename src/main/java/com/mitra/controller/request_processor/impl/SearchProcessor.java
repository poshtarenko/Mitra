package com.mitra.controller.request_processor.impl;

import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.db.filter.ProfileFilter;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Gender;
import com.mitra.entity.Instrument;
import com.mitra.entity.Speciality;
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
import java.util.stream.Collectors;

public class SearchProcessor extends AbstractRequestProcessor {

    private final LocationService locationService;
    private final InstrumentService instrumentService;
    private final SpecialityService specialityService;
    private final ProfileService profileService;
    private final DtoMapper<InstrumentDto, Instrument> instrumentRowMapper;
    private final DtoMapper<SpecialityDto, Speciality> specialityDtoMapper;

    public SearchProcessor(LocationService locationService, InstrumentService instrumentService,
                           SpecialityService specialityService, ProfileService profileService,
                           DtoMapper<InstrumentDto, Instrument> instrumentRowMapper,
                           DtoMapper<SpecialityDto, Speciality> specialityDtoMapper) {
        this.locationService = locationService;
        this.instrumentService = instrumentService;
        this.specialityService = specialityService;
        this.profileService = profileService;
        this.instrumentRowMapper = instrumentRowMapper;
        this.specialityDtoMapper = specialityDtoMapper;
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
        List<Gender> genders = new ArrayList<>();
        Collections.addAll(genders, Gender.values());
        if (ParameterHelper.parameterNotEmpty(request.getParameter("gender"))) {
            gender = Gender.getById(Integer.parseInt(request.getParameter("gender")));
            request.setAttribute("selectedGender", gender);
            genders.remove(gender);
        }
        request.setAttribute("genders", genders);

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

        List<LocationDto> locations = locationService.getAll();

        String city = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("city"))) {
            city = request.getParameter("city");
            request.setAttribute("selectedCity", city);
        }
        List<String> cities = locations.stream()
                .map(LocationDto::getCity)
                .collect(Collectors.toList());
        cities.remove(city);
        request.setAttribute("cities", cities);

        String localArea = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("localArea"))) {
            localArea = request.getParameter("localArea");
            request.setAttribute("selectedLocalArea", localArea);
        }
        List<String> localAreas = locations.stream()
                .map(LocationDto::getLocalArea)
                .collect(Collectors.toList());
        localAreas.remove(localArea);
        request.setAttribute("localAreas", localAreas);

        String region = null;
        if (ParameterHelper.parameterNotEmpty(request.getParameter("region"))) {
            region = request.getParameter("region");
            request.setAttribute("selectedRegion", region);
        }
        List<String> regions = locations.stream()
                .map(LocationDto::getRegion)
                .collect(Collectors.toList());
        regions.remove(region);
        request.setAttribute("regions", regions);

        List<InstrumentDto> instruments = new ArrayList<>();
        List<InstrumentDto> instrumentsToJSP = instrumentService.getAll();
        List<InstrumentDto> instrumentsToRemove = new ArrayList<>();
        if (ParameterHelper.parameterNotEmpty(request.getParameter("instruments"))) {
            for (String instrumentId : request.getParameterValues("instruments")) {
                int id = Integer.parseInt(instrumentId);
                for (InstrumentDto instrumentDto : instrumentsToJSP) {
                    if (instrumentDto.getId() == id) {
                        instruments.add(instrumentDto);
                        instrumentsToRemove.add(instrumentDto);
                    }
                }
            }
            request.setAttribute("selectedInstruments", instruments);
            instrumentsToJSP.removeAll(instrumentsToRemove);
        }
        request.setAttribute("instruments", instrumentsToJSP);


        List<SpecialityDto> specialities = new ArrayList<>();
        List<SpecialityDto> specialitiesToJSP = specialityService.getAll();
        List<SpecialityDto> specialitiesToRemove = new ArrayList<>();
        if (ParameterHelper.parameterNotEmpty(request.getParameter("specialities"))) {
            for (String specialityId : request.getParameterValues("specialities")) {
                int id = Integer.parseInt(specialityId);
                for (SpecialityDto specialityDto : specialitiesToJSP) {
                    if (specialityDto.getId() == id) {
                        specialities.add(specialityDto);
                        specialitiesToRemove.add(specialityDto);
                    }
                }
            }
            request.setAttribute("selectedSpecialities", specialities);
            specialitiesToJSP.removeAll(specialitiesToRemove);
        }
        request.setAttribute("specialities", specialitiesToJSP);


        ProfileFilter profileFilter = ProfileFilter.builder()
                .name(name)
                .gender(gender)
                .minAge(minAge)
                .maxAge(maxAge)
                .city(city)
                .localArea(localArea)
                .region(region)
                .instruments(instruments.stream().
                        map(instrumentRowMapper::mapToEntity)
                        .collect(Collectors.toList()))
                .specialities(specialities.stream().
                        map(specialityDtoMapper::mapToEntity)
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

        forward(request, response, AppUrl.SEARCH.getJspFileName());
    }
}
