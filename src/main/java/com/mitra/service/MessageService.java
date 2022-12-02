package com.mitra.service;

import com.mitra.dto.ChatDto;
import com.mitra.dto.MessageDto;

import java.util.List;

public interface MessageService {

    void sendMessage(MessageDto message);

    List<MessageDto> getChatMessages(int chatId);

}
