package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.LikeDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Reaction;
import com.mitra.service.ProfileLikeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LikesProcessor extends AbstractRequestProcessor {

    private final ProfileLikeService profileLikeService;

    public LikesProcessor(ProfileLikeService profileLikeService) {
        this.profileLikeService = profileLikeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());

        List<LikeDto> likes = profileLikeService.getProfileLikes(myId);

        request.setAttribute("ownLikes", profileLikeService.getOwnWithoutResponseLikes(myId, likes));
        request.setAttribute("waitingResponseLikes", profileLikeService.getWaitingResponseLikes(myId, likes));
        request.setAttribute("mutualLikes", profileLikeService.getMutualLikes(myId, likes));

        forward(request, response, UrlPath.LIKES.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.equals("")) {
            redirect(response, UrlPath.MY_PROFILE.getUrl());
            return;
        }
        int anotherUserId = Integer.parseInt(idParam);

        int userId = ((UserDto) request.getSession().getAttribute(SessionAttributes.USER_ID.name())).getId();

        if (type.equalsIgnoreCase("LIKE")) {
            profileLikeService.like(userId, anotherUserId);
        } else if (type.equalsIgnoreCase("RESPONSE")) {
            String reaction = request.getParameter("reaction");
            if (reaction.equalsIgnoreCase("LIKE"))
                profileLikeService.makeResponseOnLike(anotherUserId, userId, Reaction.LIKE);
            if (reaction.equalsIgnoreCase("DISLIKE"))
                profileLikeService.makeResponseOnLike(anotherUserId, userId, Reaction.DISLIKE);
            if (reaction.equalsIgnoreCase("IGNORE"))
                profileLikeService.makeResponseOnLike(anotherUserId, userId, Reaction.IGNORE);
        }

        String referer = request.getHeader("referer");
        int index = referer.indexOf("/app");
        redirect(response, referer.substring(index));
    }
}
