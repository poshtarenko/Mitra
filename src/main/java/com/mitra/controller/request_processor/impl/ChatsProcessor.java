package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.controller.request_processor.util.SessionAttrAccessor;
import com.mitra.dto.ChatDto;
import com.mitra.dto.LikeDto;
import com.mitra.dto.mapper.ChatDtoMapper;
import com.mitra.entity.Reaction;
import com.mitra.exception.BadRequestException;
import com.mitra.service.ChatService;
import com.mitra.service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ChatsProcessor extends AbstractRequestProcessor {

    private final ChatService chatService;
    private final LikeService likeService;

    public ChatsProcessor(ChatService chatService, LikeService likeService) {
        this.chatService = chatService;
        this.likeService = likeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        List<ChatDto> chats = chatService.getProfileChats(myId).stream()
                .map(chatDto -> ChatDtoMapper.suit(chatDto, myId))
                .collect(Collectors.toList());
        request.setAttribute("chats", chats);
        forward(request, response, AppUrl.CHATS.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        int profileId = Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "profileId"));

        LikeDto like = likeService.getLike(myId, profileId).orElseThrow(BadRequestException::new);
        if (like.getReaction() == Reaction.LIKE) {
            int chatId = chatService.startChat(myId, profileId);
            redirect(response, AppUrl.CHAT.getUrl() + "?c=" + chatId);
            return;
        }
        redirect(response, AppUrl.CHATS.getUrl());
    }
}
