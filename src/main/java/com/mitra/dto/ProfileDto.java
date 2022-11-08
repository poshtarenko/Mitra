package com.mitra.dto;

import com.mitra.entity.Gender;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ProfileDto implements Dto {
    String name;
    Integer age;
    Gender gender;
    LocationDto location;
    String text;
    List<InstrumentDto> instruments;
}
