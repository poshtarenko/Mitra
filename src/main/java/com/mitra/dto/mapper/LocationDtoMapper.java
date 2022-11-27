package com.mitra.dto.mapper;

import com.mitra.dto.LocationDto;
import com.mitra.entity.impl.LocationImpl;

public class LocationDtoMapper implements DtoMapper<LocationDto, LocationImpl> {
    @Override
    public LocationImpl mapToEntity(LocationDto dto) {
        return LocationImpl.builder()
                .city(dto.getCity())
                .localArea(dto.getLocalArea())
                .region(dto.getRegion())
                .country(dto.getCountry())
                .build();
    }

    @Override
    public LocationDto mapToDto(LocationImpl entity) {
        return LocationDto.builder()
                .city(entity.getCity())
                .localArea(entity.getLocalArea())
                .region(entity.getRegion())
                .country(entity.getCountry())
                .build();
    }
}
