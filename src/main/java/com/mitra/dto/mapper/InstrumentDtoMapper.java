package com.mitra.dto.mapper;

import com.mitra.dto.InstrumentDto;
import com.mitra.entity.Instrument;

public class InstrumentDtoMapper implements DtoMapper<InstrumentDto, Instrument> {
    @Override
    public Instrument mapToEntity(InstrumentDto dto) {
        return new Instrument(dto.getId(), dto.getName());
    }

    @Override
    public InstrumentDto mapToDto(Instrument entity) {
        return InstrumentDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
