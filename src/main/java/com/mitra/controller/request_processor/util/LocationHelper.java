package com.mitra.controller.request_processor.util;

import com.mitra.dto.LocationDto;

public final class LocationHelper {

    public static String locationDtoToString(LocationDto locationDto) {
        String delimiter = ", ";
        String stringBuilder = locationDto.getCity() +
                delimiter +
                locationDto.getLocalArea() +
                delimiter +
                locationDto.getRegion() +
                delimiter +
                locationDto.getCountry();
        return stringBuilder;
    }

    public static LocationDto stringToLocationDto(String string) {
        String[] split = string.split(", ");
        return LocationDto.builder()
                .city(split[0])
                .localArea(split[1])
                .region(split[2])
                .country(split[3])
                .build();
    }
}
