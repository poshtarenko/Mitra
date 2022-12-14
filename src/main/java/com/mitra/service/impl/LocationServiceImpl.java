package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.LocationDao;
import com.mitra.dto.LocationDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Location;
import com.mitra.exception.DaoException;
import com.mitra.service.LocationService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationDao locationDao;
    private final DtoMapper<LocationDto, Location> locationDtoMapper;

    public LocationServiceImpl(LocationDao locationDao, DtoMapper<LocationDto, Location> locationDtoMapper) {
        this.locationDao = locationDao;
        this.locationDtoMapper = locationDtoMapper;
    }

    @Override
    public List<LocationDto> getAll() {
        try (Connection connection = ConnectionManager.get()) {
            return locationDao.findAll(connection).stream()
                    .map(locationDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (DaoException | SQLException e) {
            log.error("Getting all locations failed");
            return Collections.emptyList();
        }
    }
}
