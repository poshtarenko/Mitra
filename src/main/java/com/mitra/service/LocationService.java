package com.mitra.service;

import com.mitra.dto.LocationDto;

import java.util.List;

public interface LocationService {

    /**
     * Get all locations
     *
     * @return list of locations
     */
    List<LocationDto> getAll();

}
