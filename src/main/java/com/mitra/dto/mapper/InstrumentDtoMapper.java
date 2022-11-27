package com.mitra.dto.mapper;

import com.mitra.dto.InstrumentDto;
import com.mitra.entity.impl.InstrumentImpl;

public class InstrumentDtoMapper implements DtoMapper<InstrumentDto, InstrumentImpl> {
    @Override
    public InstrumentImpl mapToEntity(InstrumentDto dto) {
        return new InstrumentImpl(dto.getId(), dto.getName());
    }

    @Override
    public InstrumentDto mapToDto(InstrumentImpl entity) {
        return InstrumentDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
