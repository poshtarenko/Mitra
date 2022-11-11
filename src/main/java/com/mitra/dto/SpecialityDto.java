package com.mitra.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SpecialityDto implements Dto {
    Integer id;
    String name;
}
