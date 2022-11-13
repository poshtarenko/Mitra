package com.mitra.db.dao;

import com.mitra.entity.Like;
import com.mitra.entity.Reaction;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface LikeDao {

    List<Like> getProfileLikes(Connection connection, int profileId);

    Optional<Like> findBySenderAndReceiver(Connection connection, int senderId, int receiverId);

    void like(Connection connection, int senderId, int receiverId);

    void makeResponse(Connection connection, int senderId, int receiverId, Reaction reaction);

}
