package com.mitra.entity.dummy;

import com.mitra.entity.Gender;
import com.mitra.entity.Instrument;
import com.mitra.entity.Like;
import com.mitra.entity.Profile;
import com.mitra.entity.impl.LocationImpl;
import com.mitra.entity.impl.SpecialityImpl;
import com.mitra.entity.impl.TrackImpl;

import java.util.List;

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
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public Integer getAge() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public Gender getGender() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public LocationImpl getLocation() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public String getText() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public String getPhotoPath() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public List<Instrument> getInstruments() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public List<SpecialityImpl> getSpecialities() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public List<Like> getLikes() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public List<TrackImpl> getTracks() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public TrackImpl getPreviewTrack() {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setName(String name) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setAge(Integer age) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setGender(Gender gender) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setLocation(LocationImpl location) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setText(String text) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setPhotoPath(String photoPath) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setInstruments(List<Instrument> instruments) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setSpecialities(List<SpecialityImpl> specialities) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setLikes(List<Like> likes) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setTracks(List<TrackImpl> tracks) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setPreviewTrack(TrackImpl previewTrack) {
        throw DummyHelper.getUnsupportedOperationExceptionAndLog();
    }
}
