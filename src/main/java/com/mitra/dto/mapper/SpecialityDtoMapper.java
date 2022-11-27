package com.mitra.dto.mapper;

import com.mitra.dto.SpecialityDto;
import com.mitra.entity.Speciality;
import com.mitra.entity.impl.SpecialityImpl;

public class SpecialityDtoMapper implements DtoMapper<SpecialityDto, Speciality> {
    @Override
    public Speciality mapToEntity(SpecialityDto dto) {
        return new SpecialityImpl(dto.getId(), dto.getName());
    }

    @Override
    public SpecialityDto mapToDto(Speciality entity) {
        return SpecialityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
