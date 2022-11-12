package com.mitra.service;

import com.mitra.dto.LikeDto;
import com.mitra.entity.Reaction;

import java.util.List;

public interface ProfileLikesService {

    void like(int senderId, int receiverId);

    void makeResponseOnLike(int senderId, int receiverId, Reaction reaction);

    List<LikeDto> getProfileLikes(int profileId);

    List<LikeDto> getOwnLikes(int profileId, List<LikeDto> profileLikes);

    List<LikeDto> getWaitingResponseLikes(int profileId, List<LikeDto> profileLikes);

    List<LikeDto> getMutualLikes(int profileId, List<LikeDto> profileLikes);

}
