package com.mitra.service;

import com.mitra.dto.ChatDto;
import com.mitra.dto.MessageDto;

import java.util.List;

public interface MessageService {

    /**
     * Send message
     *
     * @param message message
     */
    void sendMessage(MessageDto message);

    /**
     * Get all chat messages
     *
     * @param chatId chat id
     * @return list of chat messages
     */
    List<MessageDto> getChatMessages(int chatId);

}
