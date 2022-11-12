package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.LikeDao;
import com.mitra.dto.LikeDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Like;
import com.mitra.entity.Reaction;
import com.mitra.service.ProfileLikesService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileLikeServiceImpl implements ProfileLikesService {

    private final LikeDao likeDao;
    private final DtoMapper<LikeDto, Like> likeDtoMapper;

    public ProfileLikeServiceImpl(LikeDao likeDao, DtoMapper<LikeDto, Like> likeDtoMapper) {
        this.likeDao = likeDao;
        this.likeDtoMapper = likeDtoMapper;
    }

    @Override
    public void like(int senderId, int receiverId) {
        try (Connection connection = ConnectionManager.get()) {
            likeDao.like(connection, senderId, receiverId);
        } catch (SQLException e) {
            // TODO : log
        }
    }

    @Override
    public void makeResponseOnLike(int senderId, int receiverId, Reaction reaction) {
        try (Connection connection = ConnectionManager.get()) {
            likeDao.makeResponse(connection, senderId, receiverId, reaction);
        } catch (SQLException e) {
            // TODO : log
        }
    }

    @Override
    public List<LikeDto> getOwnLikes(int profileId, List<LikeDto> profileLikes) {
        return profileLikes.stream()
                .filter(like -> like.getSenderId() == profileId)
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeDto> getWaitingResponseLikes(int profileId, List<LikeDto> profileLikes) {
        return profileLikes.stream()
                .filter(like -> like.getReceiverId() == profileId)
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeDto> getMutualLikes(int profileId, List<LikeDto> profileLikes) {
        return profileLikes.stream()
                .filter(like -> like.getReaction() == Reaction.LIKE)
                .collect(Collectors.toList());
    }


}
