package com.mitra.db.mapper.impl;

import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Message;
import com.mitra.entity.Profile;
import com.mitra.entity.dummy.DummyChat;
import com.mitra.entity.impl.MessageImpl;
import com.mitra.entity.impl.ProfileImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRowMapper implements RowMapper<Message> {
    @Override
    public Message map(ResultSet resultSet) throws SQLException {
        Profile sender = ProfileImpl.builder()
                .id(resultSet.getInt(6))
                .name(resultSet.getString(7))
                .photoPath(resultSet.getString(8))
                .build();

        return new MessageImpl(
                resultSet.getLong(1),
                sender,
                new DummyChat(resultSet.getInt(2)),
                resultSet.getString(3),
                resultSet.getTimestamp(4).toLocalDateTime(),
                resultSet.getBoolean(5)
        );
    }
}
