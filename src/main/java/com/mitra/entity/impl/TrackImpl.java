package com.mitra.entity.impl;

import com.mitra.entity.Profile;
import com.mitra.entity.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackImpl implements Track {
    Integer id;
    String name;
    String author;
    String filePath;
    Profile owner;
}
