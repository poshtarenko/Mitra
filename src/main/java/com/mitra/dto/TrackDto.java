package com.mitra.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TrackDto implements Dto {
    Integer id;
    String name;
    String author;
    String filePath;
    Integer ownerId;
}
