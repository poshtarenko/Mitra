package com.mitra.dto.mapper;

import com.mitra.dto.ChatDto;
import com.mitra.dto.MessageDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Message;
import com.mitra.entity.Profile;
import com.mitra.entity.impl.ChatImpl;
import com.mitra.entity.impl.MessageImpl;

public class MessageDtoMapper implements DtoMapper<MessageDto, Message> {

    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;

    public MessageDtoMapper(DtoMapper<ProfileDto, Profile> profileDtoMapper) {
        this.profileDtoMapper = profileDtoMapper;
    }

    @Override
    public Message mapToEntity(MessageDto dto) {
        return new MessageImpl(
                dto.getId(),
                profileDtoMapper.mapToEntity(dto.getSender()),
                new ChatImpl(dto.getChat().getId(), null, null, null),
                dto.getMessage(),
                dto.getTime(),
                dto.isRead()
        );
    }

    @Override
    public MessageDto mapToDto(Message entity) {
        return new MessageDto(
                entity.getId(),
                profileDtoMapper.mapToDto(entity.getSender()),
                new ChatDto(entity.getChat().getId(), null, null, null),
                entity.getMessage(),
                entity.getTime(),
                entity.isRead()
        );
    }
}
