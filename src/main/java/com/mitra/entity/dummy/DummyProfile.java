package com.mitra.entity.dummy;

import com.mitra.entity.*;
import com.mitra.entity.impl.LocationImpl;
import com.mitra.entity.impl.TrackImpl;

import java.util.List;
import java.util.Objects;

public class DummyProfile implements Profile {

    private Integer id;

    public DummyProfile(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public Integer getAge() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public Gender getGender() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public LocationImpl getLocation() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public String getText() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public String getPhotoPath() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public List<Instrument> getInstruments() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public List<Speciality> getSpecialities() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public List<Like> getLikes() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public List<Track> getTracks() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public TrackImpl getPreviewTrack() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setName(String name) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setAge(Integer age) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setGender(Gender gender) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setLocation(Location location) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setText(String text) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setPhotoPath(String photoPath) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setInstruments(List<Instrument> instruments) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setSpecialities(List<Speciality> specialities) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setLikes(List<Like> likes) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setTracks(List<Track> tracks) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setPreviewTrack(Track previewTrack) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

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
