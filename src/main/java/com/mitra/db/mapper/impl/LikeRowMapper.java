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
                .id(resultSet.getInt(11))
                .city(resultSet.getString(12))
                .localArea(resultSet.getString(13))
                .region(resultSet.getString(14))
                .country(resultSet.getString(15))
                .build();

        Profile sender = ProfileImpl.builder()
                .id(resultSet.getInt(5))
                .name(resultSet.getString(6))
                .age(resultSet.getInt(7))
                .gender(Gender.valueOf(resultSet.getString(8)))
                .text(resultSet.getString(9))
                .photoPath(resultSet.getString(10))
                .location(senderLocation)
                .build();

        Location receiverLocation = LocationImpl.builder()
                .id(resultSet.getInt(22))
                .city(resultSet.getString(23))
                .localArea(resultSet.getString(24))
                .region(resultSet.getString(25))
                .country(resultSet.getString(26))
                .build();

        Profile receiver = ProfileImpl.builder()
                .id(resultSet.getInt(16))
                .name(resultSet.getString(17))
                .age(resultSet.getInt(18))
                .gender(Gender.valueOf(resultSet.getString(19)))
                .text(resultSet.getString(20))
                .photoPath(resultSet.getString(21))
                .location(receiverLocation)
                .build();

        return new LikeImpl(
                resultSet.getInt(1),
                sender,
                receiver,
                Reaction.getReactionByCode(resultSet.getInt(4))
        );
    }
}
