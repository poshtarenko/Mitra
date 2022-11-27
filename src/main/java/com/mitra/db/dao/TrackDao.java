package com.mitra.db.dao;

import com.mitra.entity.Track;

import java.sql.Connection;
import java.util.List;

public interface TrackDao extends Dao<Integer, Track> {

    /**
     * Get list of all profile tracks
     *
     * @param connection connection to db
     * @param profileId  profileId
     * @return list of tracks which belong to profile
     */
    List<Track> getProfileMusic(Connection connection, int profileId);

}
