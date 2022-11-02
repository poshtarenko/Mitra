package com.mitra.dto;

import com.mitra.entity.Gender;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProfileDto implements Dto {
    String name;
    Integer age;
    Gender gender;
    String location;
    String text;
}
