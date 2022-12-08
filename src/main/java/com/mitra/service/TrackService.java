package com.mitra.service;

import com.mitra.dto.TrackDto;

import java.io.InputStream;
import java.util.List;

public interface TrackService {

    /**
     * Get all profile music
     *
     * @param profileId profile id
     * @return list of profile tracks
     */
    List<TrackDto> getProfileMusic(int profileId);

    /**
     * Set preview track to profile
     *
     * @param profileId profile id
     * @param trackId   track id
     */
    void setProfilePreviewTrack(int profileId, int trackId);

    /**
     * Save track
     *
     * @param track            track
     * @param trackInputStream input stream
     */
    void save(TrackDto track, InputStream trackInputStream);

    /**
     * Remove track by id
     *
     * @param trackId track id
     */
    void remove(int trackId);
}
