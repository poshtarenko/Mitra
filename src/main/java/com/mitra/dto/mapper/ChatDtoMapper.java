package com.mitra.dto.mapper;

import com.mitra.dto.ChatDto;
import com.mitra.dto.MessageDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Chat;
import com.mitra.entity.Message;
import com.mitra.entity.Profile;
import com.mitra.entity.impl.ChatImpl;

import static java.util.stream.Collectors.*;

public class ChatDtoMapper implements DtoMapper<ChatDto, Chat> {

    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;
    private final DtoMapper<MessageDto, Message> messageDtoMapper;

    public ChatDtoMapper(DtoMapper<ProfileDto, Profile> profileDtoMapper,
                         DtoMapper<MessageDto, Message> messageDtoMapper) {
        this.profileDtoMapper = profileDtoMapper;
        this.messageDtoMapper = messageDtoMapper;
    }

    @Override
    public Chat mapToEntity(ChatDto dto) {
        return new ChatImpl(
                dto.getId(),
                profileDtoMapper.mapToEntity(dto.getMyProfile()),
                profileDtoMapper.mapToEntity(dto.getFriendProfile()),
                dto.getMessages().stream()
                        .map(messageDtoMapper::mapToEntity)
                        .collect(toList())
        );
    }

    @Override
    public ChatDto mapToDto(Chat entity) {
        return new ChatDto(
                entity.getId(),
                profileDtoMapper.mapToDto(entity.getFirstProfile()),
                profileDtoMapper.mapToDto(entity.getSecondProfile()),
                entity.getMessages().stream()
                        .map(messageDtoMapper::mapToDto)
                        .collect(toList())
        );
    }

    public static ChatDto suit(ChatDto chat, int myId) {
        if (chat.getMyProfile().getId() == myId) {
            return chat;
        } else {
            return new ChatDto(
                    chat.getId(),
                    chat.getFriendProfile(),
                    chat.getMyProfile(),
                    chat.getMessages()
            );
        }
    }
}
