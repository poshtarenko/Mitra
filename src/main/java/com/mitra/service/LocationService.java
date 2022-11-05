package com.mitra.service;

import com.mitra.entity.Location;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface LocationService {

    List<String> getAllCities();

    Optional<Location> getById(int locationId);

    Optional<Location> getById(Connection connection, int locationId);

    Optional<Location> getByCity(String city);

    Optional<Location> getByCity(Connection connection, String city);


}
