package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.dto.ChatDto;
import com.mitra.dto.MessageDto;
import com.mitra.dto.ProfileDto;
import com.mitra.service.ChatService;
import com.mitra.service.MessageService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Optional;

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
        ParameterHelper.redirectIfParameterIsEmpty(response, request.getParameter("c"), UrlPath.CHATS.getUrl());
        int chatId = Integer.parseInt(request.getParameter("c"));

        Optional<ChatDto> chatOptional = chatService.getChat(chatId);
        if (chatOptional.isPresent()) {
            ChatDto chat = chatOptional.get();
            request.setAttribute("chat", chat);
        }
        forward(request, response, UrlPath.CHAT.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());
        int chatId = Integer.parseInt(request.getParameter("chatId"));
        Optional<ChatDto> chat = chatService.getChat(chatId);

        if (chat.isPresent()) {
            ChatDto chatDto = chat.get();
            if (chatDto.getMyProfile().getId() != myId && chatDto.getFriendProfile().getId() != myId) {
                log.warn("Trying to write message in chat where user do not participate, userId={}, chat={}",
                        myId, chatDto);
                throw new AccessDeniedException("Trying to write message in chat where user do not participate");
            }

            String msg = request.getParameter("msg");
            MessageDto message = new MessageDto(
                    0L,
                    ProfileDto.builder().id(myId).build(),
                    chatDto,
                    msg,
                    LocalDateTime.now(),
                    false
            );
            messageService.sendMessage(message);
        }
        redirect(response, UrlPath.CHAT.getUrl() + "?c=" + chatId);
    }
}
