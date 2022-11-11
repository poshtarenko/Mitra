package com.mitra.dto.mapper;

import com.mitra.dto.LocationDto;
import com.mitra.entity.Location;

import java.sql.Connection;

public class LocationDtoMapper implements DtoMapper<LocationDto, Location> {
    @Override
    public Location mapToEntity(LocationDto dto) {
        return Location.builder()
                .city(dto.getCity())
                .localArea(dto.getLocalArea())
                .region(dto.getRegion())
                .country(dto.getCountry())
                .build();
    }

    @Override
    public LocationDto mapToDto(Location entity) {
        return LocationDto.builder()
                .city(entity.getCity())
                .localArea(entity.getLocalArea())
                .region(entity.getRegion())
                .country(entity.getCountry())
                .build();
    }
}
