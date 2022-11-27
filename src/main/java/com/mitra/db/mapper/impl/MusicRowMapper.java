package com.mitra.db.mapper.impl;

import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Track;
import com.mitra.entity.dummy.DummyProfile;
import com.mitra.entity.impl.TrackImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MusicRowMapper implements RowMapper<Track> {
    @Override
    public Track map(ResultSet resultSet) throws SQLException {
        return new TrackImpl(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                new DummyProfile(resultSet.getInt(5))
        );
    }
}
