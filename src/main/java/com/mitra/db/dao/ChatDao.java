package com.mitra.db.dao;

import com.mitra.entity.Chat;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ChatDao extends Dao<Integer, Chat> {
    /**
     * Get list of all chats of profile
     *
     * @param connection connection to db
     * @param profileId  profile id
     * @return list of all chats of profile
     */
    List<Chat> getProfileChats(Connection connection, int profileId);

    /**
     * Get chat between two users
     *
     * @param connection      connection to db
     * @param firstProfileId  profile id
     * @param secondProfileId profile id
     * @return chat between two users
     */
    Optional<Chat> find(Connection connection, int firstProfileId, int secondProfileId);
}
