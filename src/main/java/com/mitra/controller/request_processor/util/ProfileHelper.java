package com.mitra.controller.request_processor.util;

import com.mitra.dto.InstrumentDto;
import com.mitra.dto.SpecialityDto;
import com.mitra.service.InstrumentService;
import com.mitra.service.SpecialityService;

import java.util.List;
import java.util.stream.Collectors;

public final class ProfileHelper {

    public static List<String> getInstrumentsAsStrings(InstrumentService instrumentService){
         return instrumentService.getAll().stream()
                .map(InstrumentDto::getName)
                .collect(Collectors.toList());
    }

    public static List<String> getSpecialitiesAsStrings(SpecialityService specialityService){
        return specialityService.getAll().stream()
                .map(SpecialityDto::getName)
                .collect(Collectors.toList());
    }

    public static List<String> getProfileInstrumentsAsStrings(InstrumentService instrumentService, int profileId){
        return instrumentService.getProfileInstruments(profileId).stream()
                .map(InstrumentDto::getName)
                .collect(Collectors.toList());
    }

    public static List<String> getProfileSpecialitiesAsStrings(SpecialityService specialityService, int profileId){
        return specialityService.getProfileSpecialities(profileId).stream()
                .map(SpecialityDto::getName)
                .collect(Collectors.toList());
    }
}
