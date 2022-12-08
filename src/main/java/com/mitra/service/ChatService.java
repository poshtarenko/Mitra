package com.mitra.service;

import com.mitra.dto.ChatDto;

import java.util.List;
import java.util.Optional;

public interface ChatService {

    /**
     * Start new chat between users
     *
     * @param firstProfileId  first profile id
     * @param secondProfileId second profile id
     * @return chat id
     */
    int startChat(int firstProfileId, int secondProfileId);

    /**
     * Get all profile chats
     *
     * @param profileId profile id
     * @return list of profile chats
     */
    List<ChatDto> getProfileChats(int profileId);

    /**
     * Get chat by users ids
     *
     * @param firstProfileId  first profile id
     * @param secondProfileId second profile id
     * @return optional of chat
     */
    Optional<ChatDto> getChat(int firstProfileId, int secondProfileId);

    /**
     * Get chat by id
     *
     * @param chatId chat id
     * @return optional of chat
     */
    Optional<ChatDto> getChat(int chatId);

}
