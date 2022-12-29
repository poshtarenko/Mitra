package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.LikeDto;
import com.mitra.entity.Reaction;
import com.mitra.exception.BadRequestException;
import com.mitra.service.ChatService;
import com.mitra.service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenChatController implements PostController {

    private final ChatService chatService;
    private final LikeService likeService;

    public OpenChatController(ChatService chatService, LikeService likeService) {
        this.chatService = chatService;
        this.likeService = likeService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        int profileId = Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "profileId"));

        // todo : move this login to service
        LikeDto like = likeService.getLike(myId, profileId).orElseThrow(BadRequestException::new);
        if (like.getReaction() == Reaction.LIKE) {
            int chatId = chatService.startChat(myId, profileId);
            response.sendRedirect(GetUrl.CHAT.getUrl() + "?c=" + chatId);
            return;
        }
        ControllerUtils.redirectOnReferer(request, response);
    }
}
