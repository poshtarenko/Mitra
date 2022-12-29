package com.mitra.controller.impl.post;

import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.entity.Reaction;
import com.mitra.service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PutLikeController implements PostController {

    private final LikeService likeService;

    public PutLikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        int anotherUserId = Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "id"));

        likeService.like(myId, anotherUserId);

        ControllerUtils.redirectOnReferer(request, response);
    }
}
