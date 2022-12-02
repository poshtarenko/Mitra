package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.MessageDao;
import com.mitra.dto.MessageDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Message;
import com.mitra.service.MessageService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {

    private final MessageDao messageDao;
    private final DtoMapper<MessageDto, Message> messageDtoMapper;

    public MessageServiceImpl(MessageDao messageDao, DtoMapper<MessageDto, Message> messageDtoMapper) {
        this.messageDao = messageDao;
        this.messageDtoMapper = messageDtoMapper;
    }

    @Override
    public void sendMessage(MessageDto message) {
        try (Connection connection = ConnectionManager.get()) {
            messageDao.save(connection, messageDtoMapper.mapToEntity(message));
        } catch (SQLException e) {
            // TODO : log
        }
    }

    @Override
    public List<MessageDto> getChatMessages(int chatId) {
        try (Connection connection = ConnectionManager.get()) {
            return messageDao.getChatMessages(connection, chatId).stream()
                    .map(messageDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            // TODO : log
            return Collections.emptyList();
        }
    }
}
