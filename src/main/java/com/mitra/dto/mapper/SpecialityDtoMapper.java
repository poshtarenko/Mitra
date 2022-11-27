package com.mitra.dto.mapper;

import com.mitra.dto.SpecialityDto;
import com.mitra.entity.impl.SpecialityImpl;

public class SpecialityDtoMapper implements DtoMapper<SpecialityDto, SpecialityImpl> {
    @Override
    public SpecialityImpl mapToEntity(SpecialityDto dto) {
        return new SpecialityImpl(dto.getId(), dto.getName());
    }

    @Override
    public SpecialityDto mapToDto(SpecialityImpl entity) {
        return SpecialityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
