package com.mitra.entity.impl;

import com.mitra.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImpl implements Profile {
    Integer id; // Profile.id = User.id
    String name;
    Integer age;
    Gender gender;
    Location location;
    String text;
    String photoPath;
    List<Instrument> instruments;
    List<Speciality> specialities;
    List<Like> likes;
    List<Track> tracks;
    Track previewTrack;
    List<Chat> chats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().isAssignableFrom(Profile.class)) return false;
        Profile profile = (Profile) o;
        return id.equals(profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
