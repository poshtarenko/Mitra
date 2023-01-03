package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.service.ChatService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenChatController implements PostController {

    private final ChatService chatService;

    public OpenChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        int profileId = Integer.parseInt(ParameterHelper.getNecessaryParameter(request, "profileId"));
        int chatId = chatService.startChat(myId, profileId);
        response.sendRedirect(GetUrl.CHAT.getUrl() + "?c=" + chatId);
    }
}
