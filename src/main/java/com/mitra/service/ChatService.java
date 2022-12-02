package com.mitra.service;

import com.mitra.dto.ChatDto;
import com.mitra.dto.InstrumentDto;
import com.mitra.entity.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatService {

    int startChat(int firstProfileId, int secondProfileId);

    List<ChatDto> getProfileChats(int profileId);

    Optional<ChatDto> getChat(int firstProfileId, int secondProfileId);

    Optional<ChatDto> getChat(int chatId);

}
