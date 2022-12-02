package com.mitra.db.mapper.impl;

import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.*;
import com.mitra.entity.dummy.DummyProfile;
import com.mitra.entity.impl.ChatImpl;
import com.mitra.entity.impl.LocationImpl;
import com.mitra.entity.impl.ProfileImpl;
import com.mitra.entity.impl.TrackImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class ChatRowMapper implements RowMapper<Chat> {
    @Override
    public Chat map(ResultSet resultSet) throws SQLException {
        Profile profile1 = ProfileImpl.builder()
                .id(resultSet.getInt(2))
                .name(resultSet.getString(3))
                .photoPath(resultSet.getString(4))
                .build();

        Profile profile2 = ProfileImpl.builder()
                .id(resultSet.getInt(5))
                .name(resultSet.getString(6))
                .photoPath(resultSet.getString(7))
                .build();

        return new ChatImpl(
                resultSet.getInt(1),
                profile1,
                profile2,
                Collections.emptyList()
        );
    }
}
