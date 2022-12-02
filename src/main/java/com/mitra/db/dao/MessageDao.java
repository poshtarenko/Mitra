package com.mitra.db.dao;

import com.mitra.entity.Message;

import java.sql.Connection;
import java.util.List;

public interface MessageDao extends Dao<Long, Message> {
    /**
     * Get list of all messages from chat
     *
     * @param connection connection to db
     * @param chatId     chatId
     * @return list of all messages from chat
     */
    List<Message> getChatMessages(Connection connection, int chatId);
}
