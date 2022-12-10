package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.dto.ChatDto;
import com.mitra.dto.MessageDto;
import com.mitra.entity.dummy.DummyProfile;
import com.mitra.service.ChatService;
import com.mitra.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            List<MessageDto> chatMessages = messageService.getChatMessages(chatId);
            request.setAttribute("messages", chatMessages);
        }
        forward(request, response, UrlPath.CHAT.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());
        int chatId = Integer.parseInt(request.getParameter("chatId"));
        Optional<ChatDto> chat = chatService.getChat(chatId);

        if (chat.isPresent()) {
            String msg = request.getParameter("msg");
            MessageDto message = new MessageDto(
                    0L,
                    new DummyProfile(myId),
                    chat.get(),
                    msg,
                    LocalDateTime.now(),
                    false
            );
            messageService.sendMessage(message);
        }
        redirect(response, UrlPath.CHAT.getUrl() + "?c=" + chatId);
    }
}
