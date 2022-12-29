package com.mitra.controller.impl.get;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.LikeDto;
import com.mitra.service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LikesController implements GetController {

    private final LikeService likeService;

    public LikesController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        List<LikeDto> likes = likeService.getProfileLikes(myId);

        request.setAttribute("ownLikes", likeService.extractOwnWithoutResponse(myId, likes));
        request.setAttribute("waitingResponseLikes", likeService.extractWaitingResponse(myId, likes));
        request.setAttribute("mutualLikes", likeService.extractMutual(myId, likes));

        ControllerUtils.forward(request, response, GetUrl.LIKES.getJspFileName());
    }
}
