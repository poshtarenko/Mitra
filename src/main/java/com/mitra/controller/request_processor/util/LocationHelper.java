package com.mitra.controller.request_processor.util;

import com.mitra.dto.LocationDto;

public final class LocationHelper {

    public static String locationDtoToString(LocationDto locationDto) {
        StringBuilder stringBuilder = new StringBuilder();
        String delimiter = ", ";
        stringBuilder.append(locationDto.getCity())
                .append(delimiter)
                .append(locationDto.getLocalArea())
                .append(delimiter)
                .append(locationDto.getRegion())
                .append(delimiter)
                .append(locationDto.getCountry());
        return stringBuilder.toString();
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
