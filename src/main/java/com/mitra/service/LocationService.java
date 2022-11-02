package com.mitra.service;

import com.mitra.dto.ProfileDto;
import com.mitra.entity.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    List<String> getAllCities();

    Optional<Location> getById(int locationId);

    Optional<Location> getByCity(String city);

}
