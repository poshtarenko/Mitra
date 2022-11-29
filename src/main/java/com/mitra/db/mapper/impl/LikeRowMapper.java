package com.mitra.db.mapper.impl;

import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.*;
import com.mitra.entity.impl.LikeImpl;
import com.mitra.entity.impl.LocationImpl;
import com.mitra.entity.impl.ProfileImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeRowMapper implements RowMapper<Like> {
    @Override
    public Like map(ResultSet resultSet) throws SQLException {
        Location senderLocation = LocationImpl.builder()
                .id(resultSet.getInt(10))
                .city(resultSet.getString(11))
                .localArea(resultSet.getString(12))
                .region(resultSet.getString(13))
                .country(resultSet.getString(14))
                .build();

        Profile sender = ProfileImpl.builder()
                .id(resultSet.getInt(4))
                .name(resultSet.getString(5))
                .age(resultSet.getInt(6))
                .gender(Gender.valueOf(resultSet.getString(7)))
                .text(resultSet.getString(8))
                .photoPath(resultSet.getString(9))
                .location(senderLocation)
                .build();

        Location receiverLocation = LocationImpl.builder()
                .id(resultSet.getInt(21))
                .city(resultSet.getString(22))
                .localArea(resultSet.getString(23))
                .region(resultSet.getString(24))
                .country(resultSet.getString(25))
                .build();

        Profile receiver = ProfileImpl.builder()
                .id(resultSet.getInt(15))
                .name(resultSet.getString(16))
                .age(resultSet.getInt(17))
                .gender(Gender.valueOf(resultSet.getString(18)))
                .text(resultSet.getString(19))
                .photoPath(resultSet.getString(20))
                .location(receiverLocation)
                .build();

        return new LikeImpl(
                0,
                sender,
                receiver,
                Reaction.getReactionByCode(resultSet.getInt(3))
        );
    }
}
