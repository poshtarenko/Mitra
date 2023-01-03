package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.LikeDao;
import com.mitra.dto.LikeDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Like;
import com.mitra.entity.Reaction;
import com.mitra.exception.DaoException;
import com.mitra.service.LikeService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class LikeServiceImpl implements LikeService {

    private final LikeDao likeDao;
    private final DtoMapper<LikeDto, Like> likeDtoMapper;

    public LikeServiceImpl(LikeDao likeDao, DtoMapper<LikeDto, Like> likeDtoMapper) {
        this.likeDao = likeDao;
        this.likeDtoMapper = likeDtoMapper;
    }

    @Override
    public void like(int senderId, int receiverId) {
        try (Connection connection = ConnectionManager.get()) {
            if (likeDao.findBySenderAndReceiver(connection, senderId, receiverId).isPresent()) {
                log.warn("Trying create 'like' between profiles when like is already provided," + "sender:{}, receiver:{}",
                        senderId, receiverId);
                return;
            }
            likeDao.like(connection, senderId, receiverId);
        } catch (DaoException | SQLException e) {
            log.error("Like failed");
        }
    }

    @Override
    public void makeResponseOnLike(int senderId, int receiverId, Reaction reaction) {
        try (Connection connection = ConnectionManager.get()) {
            Optional<Like> maybeLike = likeDao.findBySenderAndReceiver(connection, senderId, receiverId);
            if (!maybeLike.isPresent()) {
                log.warn("Trying to make response where there are no like" + "sender:{}, receiver:{}",
                        senderId, receiverId);
                return;
            } else if (!maybeLike.get().getReaction().equals(Reaction.NO)) {
                log.warn("Trying to make response on like which already has response" + "sender:{}, receiver:{}",
                        senderId, receiverId);
                return;
            }
            likeDao.makeResponse(connection, senderId, receiverId, reaction);
        } catch (DaoException | SQLException e) {
            log.error("Response on like failed");
        }
    }

    @Override
    public List<LikeDto> getProfileLikes(int profileId) {
        try (Connection connection = ConnectionManager.get()) {
            return likeDao.getProfileLikes(connection, profileId).stream()
                    .map(likeDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (DaoException | SQLException e) {
            log.error("Getting all profile likes failed");
            return Collections.emptyList();
        }
    }

    @Override
    public List<LikeDto> extractOwnWithoutResponse(int profileId, List<LikeDto> likes) {
        return likes.stream()
                .filter(like -> like.getSender().getId() == profileId && like.getReaction() != Reaction.LIKE)
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeDto> extractWaitingResponse(int profileId, List<LikeDto> likes) {
        return likes.stream()
                .filter(like -> like.getReceiver().getId() == profileId && like.getReaction() == Reaction.NO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeDto> extractMutual(int profileId, List<LikeDto> likes) {
        return likes.stream()
                .filter(like -> like.getReaction() == Reaction.LIKE)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LikeDto> getLike(int senderId, int receiverId) {
        try (Connection connection = ConnectionManager.get()) {
            return likeDao.findBySenderAndReceiver(connection, senderId, receiverId)
                    .map(likeDtoMapper::mapToDto);
        } catch (DaoException | SQLException e) {
            log.error("Getting like failed");
            return Optional.empty();
        }
    }
}