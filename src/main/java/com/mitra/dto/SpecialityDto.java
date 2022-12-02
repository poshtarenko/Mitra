package com.mitra.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SpecialityDto implements Dto {
    Integer id;
    String name;
}
