package com.mitra.dto.mapper;

import com.mitra.dto.InstrumentDto;
import com.mitra.entity.Instrument;
import com.mitra.entity.impl.InstrumentImpl;

public class InstrumentDtoMapper implements DtoMapper<InstrumentDto, Instrument> {
    @Override
    public Instrument mapToEntity(InstrumentDto dto) {
        return new InstrumentImpl(dto.getId(), dto.getName());
    }

    @Override
    public InstrumentDto mapToDto(Instrument entity) {
        return InstrumentDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
