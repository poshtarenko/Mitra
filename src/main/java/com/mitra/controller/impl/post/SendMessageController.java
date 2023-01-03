package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.ChatDto;
import com.mitra.dto.MessageDto;
import com.mitra.dto.ProfileDto;
import com.mitra.exception.AccessDeniedException;
import com.mitra.exception.NothingFoundException;
import com.mitra.service.ChatService;
import com.mitra.service.MessageService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class SendMessageController implements PostController {

    private final ChatService chatService;
    private final MessageService messageService;

    public SendMessageController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        int chatId = Integer.parseInt(request.getParameter("chatId"));
        ChatDto chat = chatService.getChat(chatId)
                .orElseThrow(() -> new NothingFoundException("Chat do not found"));

        checkAccess(chat, myId);

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

        response.sendRedirect(GetUrl.CHAT.getUrl() + "?c=" + chatId);
    }

    private void checkAccess(ChatDto chat, int myId) {
        if (chat.getMyProfile().getId() != myId && chat.getFriendProfile().getId() != myId) {
            log.warn("Trying to write message in chat where user do not participate, userId={}, chat={}",
                    myId, chat);
            throw new AccessDeniedException("Trying to write message in chat where user do not participate");
        }
    }
}
