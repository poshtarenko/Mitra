package com.mitra.controller.impl.get;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.LikeDto;
import com.mitra.dto.ProfileDto;
import com.mitra.service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LikesController implements GetController {

    private final LikeService likeService;

    public LikesController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        List<LikeDto> likes = likeService.getProfileLikes(myId);

        List<LikeDto> myLikes = likeService.extractOwnWithoutResponse(myId, likes);
        List<ProfileDto> likedProfiles = extractFriendsFromLikeList(myLikes, myId);

        List<LikeDto> waitingResponseLikes = likeService.extractWaitingResponse(myId, likes);
        List<ProfileDto> waitingResponseProfiles = extractFriendsFromLikeList(waitingResponseLikes, myId);

        List<LikeDto> mutualLikes = likeService.extractMutual(myId, likes);
        List<ProfileDto> friends = extractFriendsFromLikeList(mutualLikes, myId);

        request.setAttribute("ownLikes", likedProfiles);
        request.setAttribute("waitingResponseLikes", waitingResponseProfiles);
        request.setAttribute("mutualLikes", friends);

        ControllerUtils.forward(request, response, GetUrl.LIKES.getJspFileName());
    }

    private List<ProfileDto> extractFriendsFromLikeList(List<LikeDto> likes, int myId) {
        return likes.stream()
                .map(like -> {
                    if (like.getReceiver().getId() == myId)
                        return like.getSender();
                    else if (like.getSender().getId() == myId) {
                        return like.getReceiver();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}
