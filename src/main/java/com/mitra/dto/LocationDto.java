package com.mitra.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LocationDto implements Dto {
    Integer id;
    String city;
    String localArea;
    String region;
    String country;
}
