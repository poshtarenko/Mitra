package com.mitra.controller.impl.get;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.ChatDto;
import com.mitra.dto.LikeDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Reaction;
import com.mitra.exception.NothingFoundException;
import com.mitra.service.ChatService;
import com.mitra.service.LikeService;
import com.mitra.service.ProfileService;
import com.mitra.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileController implements GetController {

    private final ProfileService profileService;
    private final LikeService likeService;
    private final TrackService trackService;
    private final ChatService chatService;

    public ProfileController(ProfileService profileService, LikeService likeService, TrackService trackService, ChatService chatService) {
        this.profileService = profileService;
        this.likeService = likeService;
        this.trackService = trackService;
        this.chatService = chatService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        int profileId = Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "id"));

        if (profileId == myId) {
            response.sendRedirect(GetUrl.MY_PROFILE.getUrl());
        }

        List<LikeDto> likes = likeService.getProfileLikes(profileId);

        Optional<LikeDto> myPossibleLike = likes.stream()
                .filter(like -> like.getSender().getId() == myId && like.getReceiver().getId() == profileId)
                .findFirst();

        Optional<LikeDto> anotherPossibleLike = likes.stream()
                .filter(like -> like.getSender().getId() == profileId && like.getReceiver().getId() == myId)
                .findFirst();

        if (myPossibleLike.isPresent()) {
            LikeDto myLike = myPossibleLike.get();
            if (myLike.getReaction() == Reaction.NO || myLike.getReaction() == Reaction.IGNORE)
                request.setAttribute("waitingForResponse", "+");
            if (myLike.getReaction() == Reaction.LIKE) {
                request.setAttribute("alreadyMutualLikes", "+");
                Optional<ChatDto> chat = chatService.getChat(myId, profileId);
                chat.ifPresent(chatDto -> request.setAttribute("chatId", chatDto.getId()));
            }
            if (myLike.getReaction() == Reaction.DISLIKE)
                request.setAttribute("userRejectedUs", "+");
        } else if (anotherPossibleLike.isPresent()) {
            LikeDto anotherLike = anotherPossibleLike.get();
            if (anotherLike.getReaction() == Reaction.NO || anotherLike.getReaction() == Reaction.IGNORE)
                request.setAttribute("enableToResponse", "+");
            if (anotherLike.getReaction() == Reaction.LIKE) {
                request.setAttribute("alreadyMutualLikes", "+");
                Optional<ChatDto> chat = chatService.getChat(myId, profileId);
                chat.ifPresent(chatDto -> request.setAttribute("chatId", chatDto.getId()));
            }
            if (anotherLike.getReaction() == Reaction.DISLIKE)
                request.setAttribute("weRejectedUser", "+");
        } else {
            request.setAttribute("enableToLike", "+");
        }

        ProfileDto profile = profileService.find(profileId)
                .orElseThrow(() -> new NothingFoundException("Profile not found"));
        request.setAttribute("profile", profile);
        request.setAttribute("tracks", trackService.getProfileMusic(profileId));

        List<ProfileDto> friends = new ArrayList<>();
        for (LikeDto like : likeService.extractMutual(profileId, likes)) {
            ProfileDto friend = null;

            if (like.getReceiver().getId() == profileId)
                friend = like.getSender();
            else if (like.getSender().getId() == profileId)
                friend = like.getReceiver();

            friends.add(friend);
        }

        request.setAttribute("friends", friends);

        ControllerUtils.forward(request, response, GetUrl.PROFILE.getJspFileName());
    }
}
