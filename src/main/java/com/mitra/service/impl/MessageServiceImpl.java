package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.ChatDao;
import com.mitra.db.dao.MessageDao;
import com.mitra.dto.MessageDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Chat;
import com.mitra.entity.Message;
import com.mitra.exception.DaoException;
import com.mitra.exception.ServiceException;
import com.mitra.service.MessageService;
import com.mitra.validator.MessageValidator;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageDao messageDao;
    private final ChatDao chatDao;
    private final DtoMapper<MessageDto, Message> messageDtoMapper;
    private final MessageValidator messageValidator;

    public MessageServiceImpl(MessageDao messageDao, ChatDao chatDao,
                              DtoMapper<MessageDto, Message> messageDtoMapper,
                              MessageValidator messageValidator) {
        this.messageDao = messageDao;
        this.chatDao = chatDao;
        this.messageDtoMapper = messageDtoMapper;
        this.messageValidator = messageValidator;
    }

    @Override
    public void sendMessage(MessageDto message) {
        try (Connection connection = ConnectionManager.get()) {
            messageValidator.checkMessage(message);

            int senderId = message.getSender().getId();
            int chatId = message.getChat().getId();
            Chat chat = chatDao.find(connection, chatId)
                    .orElseThrow(() -> new ServiceException("Trying to send message to a chat that does not exist"));

            checkUserParticipatingInChat(senderId, chat);

            messageDao.save(connection, messageDtoMapper.mapToEntity(message));
        } catch (DaoException | SQLException e) {
            log.error("Sending message failed", e);
        }
    }

    private void checkUserParticipatingInChat(int senderId, Chat chat) {
        if (senderId != chat.getFirstProfile().getId() &&
                senderId != chat.getSecondProfile().getId()) {
            log.warn("User is trying to send message to chat in which he does not participate. " +
                    "User:{}, Chat:{}", senderId, chat);
            throw new ServiceException("User is trying to send message to chat in which he does not participate");
        }
    }

    @Override
    public List<MessageDto> getChatMessages(int chatId) {
        try (Connection connection = ConnectionManager.get()) {
            return messageDao.getChatMessages(connection, chatId).stream()
                    .map(messageDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (DaoException | SQLException e) {
            log.error("Getting chat messages failed", e);
            return Collections.emptyList();
        }
    }
}
