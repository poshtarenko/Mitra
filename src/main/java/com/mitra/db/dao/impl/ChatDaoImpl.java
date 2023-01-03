package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.ChatDao;
import com.mitra.db.dao.MessageDao;
import com.mitra.db.dao.impl.util.QueryExecutor;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Chat;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ChatDaoImpl implements ChatDao {

    private final QueryExecutor<Integer, Chat> queryExecutor;
    private final MessageDao messageDao;

    public ChatDaoImpl(RowMapper<Chat> chatRowMapper, MessageDao messageDao) {
        this.queryExecutor = new QueryExecutor<>(chatRowMapper);
        this.messageDao = messageDao;
    }

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s, %s, %s, %s, %s, %s FROM %s " +
                    "JOIN %s p1 ON %s = %s " +
                    "JOIN %s p2 ON %s = %s ",
            Column.CHAT.ID,
            "p1." + Column.PROFILE.ID.shortName(), "p1." + Column.PROFILE.NAME.shortName(), "p1." + Column.PROFILE.PHOTO_PATH.shortName(),
            "p2." + Column.PROFILE.ID.shortName(), "p2." + Column.PROFILE.NAME.shortName(), "p2." + Column.PROFILE.PHOTO_PATH.shortName(),
            Table.CHAT,
            Table.PROFILE, Column.CHAT.PROFILE1_ID, "p1." + Column.PROFILE.ID.shortName(),
            Table.PROFILE, Column.CHAT.PROFILE2_ID, "p2." + Column.PROFILE.ID.shortName());

    public static final String FIND_BY_PROFILE = FIND_ALL_SQL + String.format("WHERE %s = ? OR %s = ?",
            Column.CHAT.PROFILE1_ID, Column.CHAT.PROFILE2_ID);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format("WHERE %s = ?", Column.CHAT.ID);

    public static final String FIND_BY_PROFILES_SQL = FIND_ALL_SQL + String.format(
            "WHERE (%s = ? AND %s = ?) OR (%s = ? AND %s = ?) ",
            Column.CHAT.PROFILE1_ID, Column.CHAT.PROFILE2_ID, Column.CHAT.PROFILE1_ID, Column.CHAT.PROFILE2_ID);

    public static final String SAVE_SQL = String.format(
            "INSERT INTO %s (%s, %s) VALUES (?, ?)",
            Table.CHAT, Column.CHAT.PROFILE1_ID.shortName(), Column.CHAT.PROFILE2_ID.shortName());

    public static final String DELETE_SQL = String.format(
            "DELETE FROM %s WHERE %s = ?",
            Table.CHAT, Column.CHAT.ID);

    @Override
    public Optional<Chat> find(Connection connection, Integer id) throws DaoException {
        Optional<Chat> chat = queryExecutor.selectOne(connection, FIND_SQL, id);
        chat.ifPresent(c -> c.setMessages(messageDao.getChatMessages(connection, c.getId())));
        return chat;
    }

    @Override
    public Optional<Chat> find(Connection connection, int firstProfileId, int secondProfileId) {
        Optional<Chat> chat = queryExecutor.selectOne(connection, FIND_BY_PROFILES_SQL,
                firstProfileId, secondProfileId, secondProfileId, firstProfileId);
        chat.ifPresent(c -> c.setMessages(messageDao.getChatMessages(connection, c.getId())));
        return chat;
    }

    @Override
    public List<Chat> findAll(Connection connection) throws DaoException {
        return queryExecutor.selectMany(connection, FIND_ALL_SQL);
    }

    @Override
    public List<Chat> getProfileChats(Connection connection, int profileId) {
        return queryExecutor.selectMany(connection, FIND_BY_PROFILE, profileId, profileId);
    }

    @Override
    public Integer save(Connection connection, Chat entity) throws DaoException {
        return queryExecutor.insert(connection, SAVE_SQL,
                entity.getFirstProfile().getId(), entity.getSecondProfile().getId());
    }

    @Override
    public void update(Connection connection, Integer id, Chat entity) throws DaoException {
        throw new DaoException("Update not realized yet");
    }

    @Override
    public void delete(Connection connection, Integer id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }
}
