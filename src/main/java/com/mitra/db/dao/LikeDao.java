package com.mitra.db.dao;

import com.mitra.entity.Like;
import com.mitra.entity.Reaction;

import java.sql.Connection;
import java.util.List;

public interface LikeDao {

    List<Like> getProfileLikes(Connection connection, int profileId);

    void like(Connection connection, int senderId, int receiverId);

    void makeResponse(Connection connection, int senderId, int receiverId, Reaction reaction);

}
