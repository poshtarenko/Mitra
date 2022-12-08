package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.LikeDao;
import com.mitra.dto.LikeDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Like;
import com.mitra.entity.Reaction;
import com.mitra.service.ProfileLikeService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ProfileLikeServiceImpl implements ProfileLikeService {

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
            log.error("Like failed");
        }
    }

    @Override
    public void makeResponseOnLike(int senderId, int receiverId, Reaction reaction) {
        try (Connection connection = ConnectionManager.get()) {
            likeDao.makeResponse(connection, senderId, receiverId, reaction);
        } catch (SQLException e) {
            log.error("Response on like failed");
        }
    }

    @Override
    public List<LikeDto> getProfileLikes(int profileId) {
        try (Connection connection = ConnectionManager.get()) {
            return likeDao.getProfileLikes(connection, profileId).stream()
                    .map(likeDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            log.error("Getting all profile likes failed");
            return Collections.emptyList();
        }
    }

    @Override
    public List<LikeDto> getOwnWithoutResponseLikes(int profileId, List<LikeDto> likes) {
        return likes.stream()
                .filter(like -> like.getSender().getId() == profileId && like.getReaction() != Reaction.LIKE)
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeDto> getWaitingResponseLikes(int profileId, List<LikeDto> likes) {
        return likes.stream()
                .filter(like -> like.getReceiver().getId() == profileId && like.getReaction() == Reaction.NO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeDto> getMutualLikes(int profileId, List<LikeDto> likes) {
        return likes.stream()
                .filter(like -> like.getReaction() == Reaction.LIKE)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LikeDto> getLike(int senderId, int receiverId) {
        try (Connection connection = ConnectionManager.get()) {
            return likeDao.findBySenderAndReceiver(connection, senderId, receiverId)
                    .map(likeDtoMapper::mapToDto);
        } catch (SQLException e) {
            log.error("Getting like failed");
            return Optional.empty();
        }
    }
}