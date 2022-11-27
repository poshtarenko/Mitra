package com.mitra.service;

import com.mitra.dto.TrackDto;

import java.io.InputStream;
import java.util.List;

public interface TrackService {

    List<TrackDto> getProfileMusic(int profileId);

    void setProfilePreviewTrack(int profileId, int trackId);

    void save(TrackDto track, InputStream trackInputStream);

    void remove(int trackId);
}
