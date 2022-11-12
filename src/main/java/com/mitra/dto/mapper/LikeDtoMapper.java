package com.mitra.dto.mapper;

import com.mitra.dto.LikeDto;
import com.mitra.entity.Like;

public class LikeDtoMapper implements DtoMapper<LikeDto, Like> {
    @Override
    public Like mapToEntity(LikeDto dto) {
        return new Like(
                0,
                dto.getSenderId(),
                dto.getReceiverId(),
                dto.getReaction()
        );
    }

    @Override
    public LikeDto mapToDto(Like entity) {
        return LikeDto.builder()
                .senderId(entity.getSenderId())
                .receiverId(entity.getReceiverId())
                .build();
    }
}
