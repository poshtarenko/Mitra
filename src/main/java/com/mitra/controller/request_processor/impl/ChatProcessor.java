package com.mitra.controller.request_processor.impl;

import com.mitra.controller.AppUrl;
import com.mitra.controller.SessionAttributes;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.controller.request_processor.util.SessionAttrAccessor;
import com.mitra.dto.ChatDto;
import com.mitra.dto.MessageDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.mapper.ChatDtoMapper;
import com.mitra.exception.NothingFoundException;
import com.mitra.service.ChatService;
import com.mitra.service.MessageService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Slf4j
public class ChatProcessor extends AbstractRequestProcessor {

    private final ChatService chatService;
    private final MessageService messageService;

    public ChatProcessor(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        int chatId = Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "c"));

        ChatDto chat = chatService.getChat(chatId)
                .orElseThrow(() -> new NothingFoundException("Chat do not found"));
        chat = ChatDtoMapper.suit(chat, myId);

        request.setAttribute("chat", chat);
        forward(request, response, AppUrl.CHAT.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        int chatId = Integer.parseInt(request.getParameter("chatId"));
        ChatDto chat = chatService.getChat(chatId)
                .orElseThrow(() -> new NothingFoundException("Chat do not found"));

        if (chat.getMyProfile().getId() != myId && chat.getFriendProfile().getId() != myId) {
            log.warn("Trying to write message in chat where user do not participate, userId={}, chat={}",
                    myId, chat);
            throw new AccessDeniedException("Trying to write message in chat where user do not participate");
        }

        String msg = request.getParameter("msg");
        MessageDto message = new MessageDto(
                0L,
                ProfileDto.builder().id(myId).build(),
                chat,
                msg,
                LocalDateTime.now(),
                false
        );
        messageService.sendMessage(message);

        redirect(response, AppUrl.CHAT.getUrl() + "?c=" + chatId);
    }
}
