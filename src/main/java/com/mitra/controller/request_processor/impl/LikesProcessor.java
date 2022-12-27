package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.controller.request_processor.util.SessionAttrAccessor;
import com.mitra.dto.LikeDto;
import com.mitra.entity.Reaction;
import com.mitra.service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LikesProcessor extends AbstractRequestProcessor {

    private final LikeService likeService;

    public LikesProcessor(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        List<LikeDto> likes = likeService.getProfileLikes(myId);

        request.setAttribute("ownLikes", likeService.extractOwnWithoutResponse(myId, likes));
        request.setAttribute("waitingResponseLikes", likeService.extractWaitingResponse(myId, likes));
        request.setAttribute("mutualLikes", likeService.extractMutual(myId, likes));

        forward(request, response, AppUrl.LIKES.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        String type = ParameterHelper.getNecessaryParameter(request, "type");
        int anotherUserId = Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "id"));

        if (type.equalsIgnoreCase("LIKE")) {
            likeService.like(myId, anotherUserId);
        } else if (type.equalsIgnoreCase("RESPONSE")) {
            String reaction = request.getParameter("reaction");
            if (reaction.equalsIgnoreCase("LIKE"))
                likeService.makeResponseOnLike(anotherUserId, myId, Reaction.LIKE);
            if (reaction.equalsIgnoreCase("DISLIKE"))
                likeService.makeResponseOnLike(anotherUserId, myId, Reaction.DISLIKE);
            if (reaction.equalsIgnoreCase("IGNORE"))
                likeService.makeResponseOnLike(anotherUserId, myId, Reaction.IGNORE);
        }

        String referer = request.getHeader("referer");
        int index = referer.indexOf("/app");
        redirect(response, referer.substring(index));
    }
}
