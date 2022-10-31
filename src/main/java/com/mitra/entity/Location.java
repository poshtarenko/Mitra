package com.mitra.entity;

import com.mitra.entity.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Location implements Identifiable<Integer> {
    Integer id; // city.id
    String city;
    String localArea;
    String region;
    String country;
}
