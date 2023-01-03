package com.mitra.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class ChatDto implements Dto {
    Integer id;

    @JsonIgnoreProperties({"age", "gender", "location", "text", "previewTrack",
            "instruments", "gender", "specialities", "likes"})
    ProfileDto myProfile;

    @JsonIgnoreProperties({"age", "gender", "location", "text", "previewTrack",
            "instruments", "gender", "specialities", "likes"})
    ProfileDto friendProfile;

    List<MessageDto> messages;

}
