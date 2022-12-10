package com.mitra.dto;

import com.mitra.entity.Gender;
import com.mitra.entity.Track;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ProfileDto implements Dto {
    Integer id;
    String name;
    Integer age;
    Gender gender;
    LocationDto location;
    String text;
    String photoPath;
    Track previewTrack;
    List<InstrumentDto> instruments;
    List<SpecialityDto> specialities;
    List<LikeDto> likes;
}
