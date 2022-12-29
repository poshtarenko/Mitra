package com.mitra.controller.impl.get;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.ChatDto;
import com.mitra.dto.mapper.ChatDtoMapper;
import com.mitra.service.ChatService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ChatsController implements GetController {

    private final ChatService chatService;

    public ChatsController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        List<ChatDto> chats = chatService.getProfileChats(myId).stream()
                .map(chatDto -> ChatDtoMapper.suit(chatDto, myId))
                .collect(Collectors.toList());
        request.setAttribute("chats", chats);

        ControllerUtils.forward(request, response, GetUrl.CHATS.getJspFileName());
    }
}
