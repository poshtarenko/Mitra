package com.mitra.db.dao.impl;

import com.mitra.db.Column;
import com.mitra.db.Table;
import com.mitra.db.dao.LikeDao;
import com.mitra.db.dao.QueryExecutor;
import com.mitra.db.mapper.RowMapper;
import com.mitra.entity.Like;
import com.mitra.entity.Reaction;
import sun.tools.jconsole.Tab;

import java.sql.Connection;
import java.util.List;

public class LikeDaoImpl implements LikeDao {

    private final RowMapper<Like> likeRowMapper;
    private final QueryExecutor<Integer, Like> queryExecutor;

    //SELECT sender_id, receiver_id, reaction FROM likes WHERE sender_id = ? OR receiver_id = ?;
    public static final String GET_PROFILE_LIKES = String.format(
            "SELECT %s, %s, %s FROM %s WHERE %s = ? OR %s = ?",
            Column.LIKE.SENDER_ID, Column.LIKE.RECEIVER_ID, Column.LIKE.REACTION,
            Table.LIKE, Column.LIKE.SENDER_ID, Column.LIKE.RECEIVER_ID);

    public static final String LIKE = String.format(
            "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, 0)",
            Table.LIKE, Column.LIKE.SENDER_ID, Column.LIKE.RECEIVER_ID, Column.LIKE.REACTION);

    public static final String MAKE_RESPONSE = String.format(
            "UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?",
            Table.LIKE, Column.LIKE.REACTION, Column.LIKE.SENDER_ID, Column.LIKE.RECEIVER_ID);


    public LikeDaoImpl(RowMapper<Like> likeRowMapper) {
        this.likeRowMapper = likeRowMapper;
        this.queryExecutor = new QueryExecutor<>(likeRowMapper);
    }

    @Override
    public List<Like> getProfileLikes(Connection connection, int profileId) {
        return queryExecutor.findAll(connection, GET_PROFILE_LIKES, profileId, profileId);
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
