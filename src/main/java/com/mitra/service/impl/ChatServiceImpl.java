package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.ChatDao;
import com.mitra.dto.ChatDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Chat;
import com.mitra.entity.dummy.DummyProfile;
import com.mitra.entity.impl.ChatImpl;
import com.mitra.service.ChatService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChatServiceImpl implements ChatService {

    private final ChatDao chatDao;
    private final DtoMapper<ChatDto, Chat> chatDtoMapper;

    public ChatServiceImpl(ChatDao chatDao, DtoMapper<ChatDto, Chat> chatDtoMapper) {
        this.chatDao = chatDao;
        this.chatDtoMapper = chatDtoMapper;
    }

    @Override
    public int startChat(int firstProfileId, int secondProfileId) {
        try (Connection connection = ConnectionManager.get()) {
            Chat chat = new ChatImpl(0, new DummyProfile(firstProfileId), new DummyProfile(secondProfileId),
                    Collections.emptyList());
            return chatDao.save(connection, chat);
        } catch (SQLException e) {
            // TODO : log
            return 0;
        }
    }

    @Override
    public List<ChatDto> getProfileChats(int profileId) {
        try (Connection connection = ConnectionManager.get()) {
            return chatDao.getProfileChats(connection, profileId).stream()
                    .map(chatDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<ChatDto> getChat(int firstProfileId, int secondProfileId) {
        try (Connection connection = ConnectionManager.get()) {
            Optional<Chat> chat = chatDao.find(connection, firstProfileId, secondProfileId);
            return chat.map(chatDtoMapper::mapToDto);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChatDto> getChat(int chatId) {
        try (Connection connection = ConnectionManager.get()) {
            Optional<Chat> chat = chatDao.find(connection, chatId);
            return chat.map(chatDtoMapper::mapToDto);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }
}
