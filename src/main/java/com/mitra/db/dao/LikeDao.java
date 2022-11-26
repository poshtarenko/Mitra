package com.mitra.db.dao;

import com.mitra.entity.Like;
import com.mitra.entity.Reaction;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface LikeDao {

    /**
     * Get list of all profile likes
     *
     * @param connection connection to db
     * @param profileId  profileId
     * @return list of likes which user created AND which other users put on user
     */
    List<Like> getProfileLikes(Connection connection, int profileId);

    /**
     * Find like in DB by sender and receiver
     *
     * @param connection connection to db
     * @param senderId   sender profileId
     * @param receiverId receiver profileId
     * @return Optional of Like
     */
    Optional<Like> findBySenderAndReceiver(Connection connection, int senderId, int receiverId);

    /**
     * Insert new like
     *
     * @param connection connection to db
     * @param senderId   sender profileId
     * @param receiverId receiver profileId
     */
    void like(Connection connection, int senderId, int receiverId);

    /**
     * Insert new like
     *
     * @param connection connection to db
     * @param senderId   sender profileId
     * @param receiverId receiver profileId
     * @param reaction   reaction (LIKE/DISLIKE/IGNORE)
     */
    void makeResponse(Connection connection, int senderId, int receiverId, Reaction reaction);

}
