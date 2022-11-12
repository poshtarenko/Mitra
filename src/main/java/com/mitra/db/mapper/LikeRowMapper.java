package com.mitra.db.mapper;

import com.mitra.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeRowMapper implements RowMapper<Like> {
    @Override
    public Like map(ResultSet resultSet) throws SQLException {
        Location senderLocation = Location.builder()
                .city(resultSet.getString(10))
                .localArea(resultSet.getString(11))
                .region(resultSet.getString(12))
                .country(resultSet.getString(13))
                .build();

        Profile sender = Profile.builder()
                .id(resultSet.getInt(4))
                .name(resultSet.getString(5))
                .age(resultSet.getInt(6))
                .gender(Gender.valueOf(resultSet.getString(7)))
                .text(resultSet.getString(8))
                .photoPath(resultSet.getString(9))
                .location(senderLocation)
                .build();

        Location receiverLocation = Location.builder()
                .city(resultSet.getString(20))
                .localArea(resultSet.getString(21))
                .region(resultSet.getString(22))
                .country(resultSet.getString(23))
                .build();

        Profile receiver = Profile.builder()
                .id(resultSet.getInt(14))
                .name(resultSet.getString(15))
                .age(resultSet.getInt(16))
                .gender(Gender.valueOf(resultSet.getString(17)))
                .text(resultSet.getString(18))
                .photoPath(resultSet.getString(19))
                .location(receiverLocation)
                .build();

        return new Like(
                0,
                sender,
                receiver,
                Reaction.getReactionByCode(resultSet.getInt(3))
        );
    }
}
