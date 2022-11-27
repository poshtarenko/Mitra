package com.mitra.db.mapper.impl;

import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Gender;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;
import com.mitra.entity.Track;
import com.mitra.entity.dummy.DummyProfile;
import com.mitra.entity.impl.LocationImpl;
import com.mitra.entity.impl.ProfileImpl;
import com.mitra.entity.impl.TrackImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRowMapper implements RowMapper<Profile> {

    @Override
    public Profile map(ResultSet resultSet) throws SQLException {
        Location location = LocationImpl.builder()
                .id(resultSet.getInt(7))
                .city(resultSet.getString(8))
                .localArea(resultSet.getString(9))
                .region(resultSet.getString(10))
                .country(resultSet.getString(11))
                .build();

        Track previewTrack = new TrackImpl(
                resultSet.getInt(12),
                resultSet.getString(13),
                resultSet.getString(14),
                resultSet.getString(15),
                new DummyProfile(resultSet.getInt(1))
        );

        return ProfileImpl.builder()
                .id(resultSet.getInt(1))
                .name(resultSet.getString(2))
                .age(resultSet.getInt(3))
                .gender(Gender.valueOf(resultSet.getString(4)))
                .text(resultSet.getString(5))
                .photoPath(resultSet.getString(6))
                .location(location)
                .previewTrack(previewTrack)
                .build();
    }
}
