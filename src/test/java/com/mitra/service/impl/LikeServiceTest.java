package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.dto.LikeDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Reaction;
import com.mitra.service.LikeService;
import com.mitra.service.ProfileService;
import com.mitra.service.ServiceFactory;
import org.junit.jupiter.api.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LikeServiceTest {

    static ProfileService profileService;
    static LikeService likeService;

    static ProfileDto profile1;
    static int profile1Id = 139;

    static ProfileDto profile2;
    static int profile2Id = 141;

    @BeforeAll
    static void setUp() throws SQLException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        profileService = serviceFactory.getProfileService();
        likeService = serviceFactory.getLikeService();

        profile1 = profileService.find(profile1Id).get();
        profile2 = profileService.find(profile2Id).get();
    }

    @AfterAll
    static void shutdown() throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionManager.get()
                .prepareStatement("DELETE FROM \"like\" WHERE sender_id = ? AND receiver_id = ?")) {
            preparedStatement.setInt(1, profile1Id);
            preparedStatement.setInt(2, profile2Id);
            preparedStatement.executeUpdate();
        }
    }

    @Test
    @Order(1)
    void like() {
        likeService.like(profile1.getId(), profile2.getId());

        List<LikeDto> profile1Likes = likeService.getProfileLikes(profile1.getId());
        Optional<LikeDto> like1 = profile1Likes.stream()
                .filter(like -> like.getReceiver().getId().equals(profile2.getId()))
                .findFirst();

        if(!like1.isPresent()){
            fail("Поставлений лайк не відображається у того користувача, який цей лайк поставив");
        }

        List<LikeDto> profile2Likes = likeService.getProfileLikes(profile2.getId());
        Optional<LikeDto> like2 = profile2Likes.stream()
                .filter(like -> like.getSender().getId().equals(profile1.getId()))
                .findFirst();

        if(!like2.isPresent()){
            fail("Поставлений лайк не відображається у того користувача, якому поставили лайк");
        }
    }

    @Test
    @Order(2)
    void makeResponseOnLike() {
        likeService.makeResponseOnLike(profile1.getId(), profile2.getId(), Reaction.LIKE);

        List<LikeDto> profile1Likes = likeService.getProfileLikes(profile1.getId());
        Optional<LikeDto> like1 = profile1Likes.stream()
                .filter(like -> like.getReceiver().getId().equals(profile2.getId()) &&
                        like.getReaction() == Reaction.LIKE)
                .findFirst();

        if(!like1.isPresent()){
            fail("Незважаючи що другий користувач відповів лайком, у першого це не було відображено");
        }

        List<LikeDto> profile2Likes = likeService.getProfileLikes(profile2.getId());
        Optional<LikeDto> like2 = profile2Likes.stream()
                .filter(like -> like.getSender().getId().equals(profile1.getId()) &&
                        like.getReaction() == Reaction.LIKE)
                .findFirst();

        if(!like2.isPresent()){
            fail("Наша реакція ніяк не була збережена");
        }
    }

    @Test
    @Order(3)
    void getOwnWithoutResponseLikes() {
        List<LikeDto> profile1Likes = likeService.getProfileLikes(profile1.getId());
        List<LikeDto> likesWithoutResponse = profile1Likes.stream()
                .filter(like -> Objects.equals(like.getSender().getId(), profile1.getId()) && like.getReaction() != Reaction.LIKE)
                .collect(Collectors.toList());

        assertLikesArrayAreEqual(likeService.extractOwnWithoutResponse(profile1.getId(), profile1Likes),
                likesWithoutResponse);
    }

    @Test
    @Order(4)
    void getWaitingResponseLikes() {
        List<LikeDto> profile1Likes = likeService.getProfileLikes(profile1.getId());
        List<LikeDto> likesWaitingResponse = profile1Likes.stream()
                .filter(like -> Objects.equals(like.getReceiver().getId(), profile1.getId()) && like.getReaction() == Reaction.NO)
                .collect(Collectors.toList());

        assertLikesArrayAreEqual(likeService.extractWaitingResponse(profile1.getId(), profile1Likes),
                likesWaitingResponse);
    }

    @Test
    @Order(5)
    void getMutualLikes() {
        List<LikeDto> profile1Likes = likeService.getProfileLikes(profile1.getId());
        List<LikeDto> mutualLikes = profile1Likes.stream()
                .filter(like -> like.getReaction() == Reaction.LIKE)
                .collect(Collectors.toList());

        assertLikesArrayAreEqual(likeService.extractMutual(profile1.getId(), profile1Likes),
                mutualLikes);
    }

    void assertLikesArrayAreEqual(List<LikeDto> likes1, List<LikeDto> likes2) {
        for (LikeDto like1 : likes1) {
            Optional<LikeDto> equalLike = likes2.stream()
                    .filter(like2 -> like1.getSender() == like2.getSender() &&
                            like1.getReceiver() == like2.getReceiver() &&
                            like1.getReaction() == like2.getReaction())
                    .findFirst();
            if (!equalLike.isPresent()){
                fail("Масиви лайків не однакові");
            }
        }
    }
}