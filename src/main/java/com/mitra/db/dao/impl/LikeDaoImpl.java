package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.LikeDao;
import com.mitra.db.dao.QueryExecutor;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Like;
import com.mitra.entity.Reaction;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class LikeDaoImpl implements LikeDao {

    private final RowMapper<Like> likeRowMapper;
    private final QueryExecutor<Integer, Like> queryExecutor;

    public static final String GET_ALL_LIKES = String.format(
            "SELECT * FROM %s " +
                    "JOIN %s s ON s.%s = %s " +
                    "JOIN %s r ON r.%s = %s ",
            Table.LIKE,
            "profile_full", Column.PROFILE.ID.shortName(), Column.LIKE.SENDER_ID,
            "profile_full", Column.PROFILE.ID.shortName(), Column.LIKE.RECEIVER_ID);

    public static final String GET_PROFILE_LIKES = GET_ALL_LIKES + String.format(
            "WHERE %s = ? OR %s = ?",
            Column.LIKE.SENDER_ID, Column.LIKE.RECEIVER_ID);

    public static final String GET_BY_SENDER_AND_RECEIVER = GET_ALL_LIKES + String.format(
            "WHERE %s = ? AND %s = ?",
            Column.LIKE.SENDER_ID, Column.LIKE.RECEIVER_ID);

    public static final String LIKE = String.format(
            "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, 0)",
            Table.LIKE, Column.LIKE.SENDER_ID.shortName(), Column.LIKE.RECEIVER_ID.shortName(), Column.LIKE.REACTION.shortName());

    public static final String MAKE_RESPONSE = String.format(
            "UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?",
            Table.LIKE, Column.LIKE.REACTION.shortName(), Column.LIKE.SENDER_ID.shortName(), Column.LIKE.RECEIVER_ID.shortName());


    public LikeDaoImpl(RowMapper<Like> likeRowMapper) {
        this.likeRowMapper = likeRowMapper;
        this.queryExecutor = new QueryExecutor<>(likeRowMapper);
    }

    @Override
    public List<Like> getProfileLikes(Connection connection, int profileId) {
        return queryExecutor.findAll(connection, GET_PROFILE_LIKES, profileId, profileId);
    }

    @Override
    public Optional<Like> findBySenderAndReceiver(Connection connection, int senderId, int receiverId) {
        return queryExecutor.find(connection, GET_BY_SENDER_AND_RECEIVER, senderId, receiverId);
    }

    @Override
    public void like(Connection connection, int senderId, int receiverId) {
        queryExecutor.save(connection, LIKE, senderId, receiverId);
    }

    @Override
    public void makeResponse(Connection connection, int senderId, int receiverId, Reaction reaction) {
        queryExecutor.update(connection, MAKE_RESPONSE, Reaction.getCodeByReaction(reaction),
                senderId, receiverId);
    }

}
