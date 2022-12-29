package com.mitra.controller.impl.get;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.ChatDto;
import com.mitra.dto.mapper.ChatDtoMapper;
import com.mitra.exception.NothingFoundException;
import com.mitra.service.ChatService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ChatController implements GetController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        int chatId = Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "c"));

        ChatDto chat = chatService.getChat(chatId)
                .orElseThrow(() -> new NothingFoundException("Chat do not found"));
        chat = ChatDtoMapper.suit(chat, myId);

        request.setAttribute("chat", chat);
        ControllerUtils.forward(request, response, GetUrl.CHAT.getJspFileName());
    }
}
