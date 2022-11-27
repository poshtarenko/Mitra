package com.mitra.entity;

import java.util.List;

public interface Profile extends Identifiable<Integer> {
    String getName();

    Integer getAge();

    com.mitra.entity.Gender getGender();

    Location getLocation();

    String getText();

    String getPhotoPath();

    List<Instrument> getInstruments();

    List<Speciality> getSpecialities();

    List<Like> getLikes();

    List<Track> getTracks();

    Track getPreviewTrack();

    void setName(String name);

    void setAge(Integer age);

    void setGender(Gender gender);

    void setLocation(Location location);

    void setText(String text);

    void setPhotoPath(String photoPath);

    void setInstruments(List<Instrument> instruments);

    void setSpecialities(List<Speciality> specialities);

    void setLikes(List<Like> likes);

    void setTracks(List<Track> tracks);

    void setPreviewTrack(Track previewTrack);
}
