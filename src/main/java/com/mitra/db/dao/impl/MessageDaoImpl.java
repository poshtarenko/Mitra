package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.*;
import com.mitra.db.dao.impl.util.QueryExecutor;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.*;
import com.mitra.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MessageDaoImpl implements MessageDao {

    private final RowMapper<Message> messageRowMapper;
    private final QueryExecutor<Long, Message> queryExecutor;

    public MessageDaoImpl(RowMapper<Message> messageRowMapper) {
        this.messageRowMapper = messageRowMapper;
        this.queryExecutor = new QueryExecutor<>(messageRowMapper);
    }

    public static final String FIND_ALL_SQL = String.format(
            "SELECT %s, %s, %s, %s, %s, %s, %s, %s FROM %s " +
                    "JOIN %s ON %s = %s ",
            Column.MESSAGE.ID, Column.MESSAGE.CHAT_ID, Column.MESSAGE.MESSAGE, Column.MESSAGE.TIME,
            Column.MESSAGE.IS_READ, Column.PROFILE.ID, Column.PROFILE.NAME, Column.PROFILE.PHOTO_PATH,
            Table.MESSAGE,
            Table.PROFILE, Column.MESSAGE.SENDER_ID, Column.PROFILE.ID);

    public static final String FIND_SQL = FIND_ALL_SQL + String.format("WHERE %s = ?", Column.MESSAGE.ID);

    public static final String FIND_BY_CHAT_SQL = FIND_ALL_SQL + String.format(
            "WHERE %s = ? ORDER BY %s ",
            Column.MESSAGE.CHAT_ID, Column.MESSAGE.TIME);

    public static final String SAVE_SQL = String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
            Table.MESSAGE, Column.MESSAGE.SENDER_ID.shortName(), Column.MESSAGE.MESSAGE.shortName(),
            Column.MESSAGE.TIME.shortName(), Column.MESSAGE.IS_READ.shortName(), Column.MESSAGE.CHAT_ID.shortName());

    public static final String UPDATE_SQL = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
            Table.MESSAGE,
            Column.MESSAGE.SENDER_ID.shortName(), Column.MESSAGE.MESSAGE.shortName(),
            Column.MESSAGE.TIME.shortName(), Column.MESSAGE.IS_READ.shortName(),
            Column.MESSAGE.ID.shortName());

    public static final String DELETE_SQL = String.format(
            "DELETE FROM %s WHERE %s = ?",
            Table.MESSAGE, Column.MESSAGE.ID);

    @Override
    public Optional<Message> find(Connection connection, Long id) throws DaoException {
        return queryExecutor.selectOne(connection, FIND_SQL, id);
    }

    @Override
    public List<Message> findAll(Connection connection) throws DaoException {
        return queryExecutor.selectMany(connection, FIND_ALL_SQL);
    }

    @Override
    public List<Message> getChatMessages(Connection connection, int chatId) {
        return queryExecutor.selectMany(connection, FIND_BY_CHAT_SQL, chatId);
    }

    @Override
    public Long save(Connection connection, Message entity) throws DaoException {
        return queryExecutor.insert(connection, SAVE_SQL,
                entity.getSender().getId(), entity.getMessage(), entity.getTime(),
                entity.isRead(), entity.getChat().getId());
    }

    @Override
    public void update(Connection connection, Long id, Message entity) throws DaoException {
        queryExecutor.update(connection, UPDATE_SQL,
                entity.getSender().getId(), entity.getMessage(), entity.getTime(), entity.isRead(), entity.getId());
    }

    @Override
    public void delete(Connection connection, Long id) throws DaoException {
        queryExecutor.update(connection, DELETE_SQL, id);
    }
}
