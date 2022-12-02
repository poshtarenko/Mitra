package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.ChatDto;
import com.mitra.dto.LikeDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Reaction;
import com.mitra.service.ChatService;
import com.mitra.service.ProfileLikeService;
import com.mitra.service.ProfileService;
import com.mitra.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProfileProcessor extends AbstractRequestProcessor {

    private final ProfileService profileService;
    private final ProfileLikeService profileLikeService;
    private final ChatService chatService;

    public ProfileProcessor(ProfileService profileService, ProfileLikeService profileLikeService, ChatService chatService) {
        this.profileService = profileService;
        this.profileLikeService = profileLikeService;
        this.chatService = chatService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.equals("")) {
            redirect(response, UrlPath.MY_PROFILE.get());
            return;
        }
        int profileId = Integer.parseInt(idParam);

        Optional<ProfileDto> profile = profileService.find(profileId);
        if (!profile.isPresent()) {
            redirect(response, UrlPath.MY_PROFILE.get());
            return;
        }

        int myId = ((UserDto) request.getSession().getAttribute(SessionAttributes.USER.name())).getId();

        Optional<LikeDto> myPossibleLike = profileLikeService.getLike(myId, profileId);
        Optional<LikeDto> anotherPossibleLike = profileLikeService.getLike(profileId, myId);

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

        request.setAttribute("profile", profile.get());
        forward(request, response, UrlPath.PROFILE.getJspFileName());
    }
}
