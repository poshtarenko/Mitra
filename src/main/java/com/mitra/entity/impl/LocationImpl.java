package com.mitra.entity.impl;

import com.mitra.entity.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LocationImpl implements Identifiable<Integer>, com.mitra.entity.Location {
    Integer id; // city.id
    String city;
    String localArea;
    String region;
    String country;
}
