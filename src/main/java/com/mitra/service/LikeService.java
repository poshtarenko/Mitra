package com.mitra.service;

import com.mitra.dto.LikeDto;
import com.mitra.entity.Reaction;

import java.util.List;
import java.util.Optional;

public interface LikeService {

    /**
     * Put a like
     *
     * @param senderId   sender id
     * @param receiverId receiver id
     */
    void like(int senderId, int receiverId);

    /**
     * Make response on like
     *
     * @param senderId   sender id
     * @param receiverId receiver id
     * @param reaction   reaction
     */
    void makeResponseOnLike(int senderId, int receiverId, Reaction reaction);

    /**
     * Get all profile likes
     *
     * @param profileId first id
     * @return list of profile likes
     */
    List<LikeDto> getProfileLikes(int profileId);

    /**
     * Extract likes we are waiting response
     *
     * @param profileId my profile id
     * @param likes     list of likes
     * @return list of profile likes
     */
    List<LikeDto> extractOwnWithoutResponse(int profileId, List<LikeDto> likes);

    /**
     * Extract likes another user is waiting our response
     *
     * @param profileId my profile id
     * @param likes     list of likes
     * @return list of profile likes
     */
    List<LikeDto> extractWaitingResponse(int profileId, List<LikeDto> likes);

    /**
     * Extract mutual likes
     *
     * @param profileId my profile id
     * @param likes     list of likes
     * @return list of profile likes
     */
    List<LikeDto> extractMutual(int profileId, List<LikeDto> likes);

    /**
     * Get like by sender and receiver
     *
     * @param senderId   sender id
     * @param receiverId receiver id
     * @return optional of like
     */
    Optional<LikeDto> getLike(int senderId, int receiverId);

}
