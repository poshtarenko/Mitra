package com.mitra.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements Identifiable<Integer> {
    Integer id; // Profile.id = User.id
    String name;
    Integer age;
    Gender gender;
    Location location;
    String text;
    String photoPath;
    List<Instrument> instruments;
    List<Speciality> specialities;
    Track previewTrack;
    List<Track> tracks;
}
