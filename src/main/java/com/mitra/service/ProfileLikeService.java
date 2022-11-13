package com.mitra.service;

import com.mitra.dto.LikeDto;
import com.mitra.entity.Reaction;

import java.util.List;
import java.util.Optional;

public interface ProfileLikeService {

    void like(int senderId, int receiverId);

    void makeResponseOnLike(int senderId, int receiverId, Reaction reaction);

    List<LikeDto> getProfileLikes(int profileId);

    List<LikeDto> getOwnWithoutResponseLikes(int profileId, List<LikeDto> profileLikes);

    List<LikeDto> getWaitingResponseLikes(int profileId, List<LikeDto> profileLikes);

    List<LikeDto> getMutualLikes(int profileId, List<LikeDto> profileLikes);

    Optional<LikeDto> getLike(int senderId, int receiverId);

}
