package com.mitra.service;

import com.mitra.dto.SpecialityDto;

import java.util.List;

public interface SpecialityService {

    List<SpecialityDto> getAll();

    List<SpecialityDto> getProfileSpecialities(int profileId);

    void setSpecialitiesToProfile(int profileId, List<SpecialityDto> specialities);
}
