package com.mitra.dto.mapper;

import com.mitra.dto.LikeDto;
import com.mitra.dto.ProfileDto;
import com.mitra.entity.Profile;
import com.mitra.entity.impl.LikeImpl;

public class LikeDtoMapper implements DtoMapper<LikeDto, LikeImpl> {

    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;

    public LikeDtoMapper(DtoMapper<ProfileDto, Profile> profileDtoMapper) {
        this.profileDtoMapper = profileDtoMapper;
    }

    @Override
    public LikeImpl mapToEntity(LikeDto dto) {
        return new LikeImpl(
                0,
                profileDtoMapper.mapToEntity(dto.getSender()),
                profileDtoMapper.mapToEntity(dto.getReceiver()),
                dto.getReaction()
        );
    }

    @Override
    public LikeDto mapToDto(LikeImpl entity) {
        return LikeDto.builder()
                .sender(profileDtoMapper.mapToDto(entity.getSender()))
                .receiver(profileDtoMapper.mapToDto(entity.getReceiver()))
                .reaction(entity.getReaction())
                .build();
    }
}
