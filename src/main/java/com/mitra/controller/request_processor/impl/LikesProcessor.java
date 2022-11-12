package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.LikeDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;
import com.mitra.service.ProfileLikesService;
import com.mitra.service.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LikesProcessor extends AbstractRequestProcessor {

    private final ProfileLikesService profileLikesService;

    public LikesProcessor(ProfileLikesService profileLikesService) {
        this.profileLikesService = profileLikesService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int profileId = ((UserDto) request.getSession().getAttribute(SessionAttributes.USER.name())).getId();

        List<LikeDto> likes = profileLikesService.getProfileLikes(profileId);

        request.setAttribute("ownLikes", profileLikesService.getOwnLikes(profileId, likes));
        request.setAttribute("waitingResponseLikes", profileLikesService.getWaitingResponseLikes(profileId, likes));
        request.setAttribute("mutualLikes", profileLikesService.getMutualLikes(profileId, likes));

        forward(request, response, UrlPath.LIKES.getJspFileName());
    }
}
