package com.mitra.service;

import com.mitra.dto.SpecialityDto;

import java.util.List;

public interface SpecialityService {

    /**
     * Get all specialities
     *
     * @return list of specialities
     */
    List<SpecialityDto> getAll();

    /**
     * Get all profile specialities
     *
     * @param profileId profile id
     * @return list of profile specialities
     */
    List<SpecialityDto> getProfileSpecialities(int profileId);

    /**
     * Set specialities list to profile
     *
     * @param profileId    profile id
     * @param specialities specialities list
     */
    void setSpecialitiesToProfile(int profileId, List<SpecialityDto> specialities);
}
