package com.mitra.dto.mapper;

import com.mitra.dto.Dto;
import com.mitra.entity.Identifiable;

import java.sql.Connection;

public interface DtoMapper<D extends Dto, E extends Identifiable> {

    // TODO : comment

    E mapToEntity(D dto);

    D mapToDto(E entity);

}
