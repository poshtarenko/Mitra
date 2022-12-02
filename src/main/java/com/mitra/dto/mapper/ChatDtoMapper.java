package com.mitra.dto.mapper;

import com.mitra.dto.ChatDto;
import com.mitra.dto.InstrumentDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Chat;
import com.mitra.entity.Instrument;
import com.mitra.entity.Profile;
import com.mitra.entity.impl.ChatImpl;
import com.mitra.entity.impl.InstrumentImpl;

public class ChatDtoMapper implements DtoMapper<ChatDto, Chat> {

    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;

    public ChatDtoMapper(DtoMapper<ProfileDto, Profile> profileDtoMapper) {
        this.profileDtoMapper = profileDtoMapper;
    }

    @Override
    public Chat mapToEntity(ChatDto dto) {
        return new ChatImpl(
                dto.getId(),
                profileDtoMapper.mapToEntity(dto.getFirstProfile()),
                profileDtoMapper.mapToEntity(dto.getSecondProfile()),
                dto.getMessages()
        );
    }

    @Override
    public ChatDto mapToDto(Chat entity) {
        return new ChatDto(
                entity.getId(),
                profileDtoMapper.mapToDto(entity.getFirstProfile()),
                profileDtoMapper.mapToDto(entity.getSecondProfile()),
                entity.getMessages()
        );
    }
}
