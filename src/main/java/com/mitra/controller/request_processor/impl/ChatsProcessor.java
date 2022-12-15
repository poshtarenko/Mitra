package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.dto.ChatDto;
import com.mitra.dto.LikeDto;
import com.mitra.entity.Reaction;
import com.mitra.service.ChatService;
import com.mitra.service.ProfileLikeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ChatsProcessor extends AbstractRequestProcessor {

    private final ChatService chatService;
    private final ProfileLikeService profileLikeService;

    public ChatsProcessor(ChatService chatService, ProfileLikeService profileLikeService) {
        this.chatService = chatService;
        this.profileLikeService = profileLikeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());
        List<ChatDto> chats = chatService.getProfileChats(myId);
        request.setAttribute("chats", chats);
        forward(request, response, UrlPath.CHATS.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());
        int profileId = Integer.parseInt(request.getParameter("profileId"));

        Optional<LikeDto> like = profileLikeService.getLike(myId, profileId);
        if (like.isPresent() && like.get().getReaction() == Reaction.LIKE) {
            int chatId = chatService.startChat(myId, profileId);
            redirect(response, UrlPath.CHAT.getUrl() + "?c=" + chatId);
            return;
        }
        redirect(response, UrlPath.CHATS.getUrl());
    }
}
