package com.mitra.dto.mapper;

import com.mitra.dto.Dto;
import com.mitra.entity.Identifiable;

import java.sql.Connection;

public interface DtoMapper<D extends Dto, E extends Identifiable> {

    /**
     * Do mapping from DTO to POJO entity
     *
     * @param dto DTO object
     * @return POJO entity
     */
    E mapToEntity(D dto);

    /**
     * Do mapping from POJO entity to DTO
     *
     * @param entity POJO entity
     * @return DTO object
     */
    D mapToDto(E entity);

}
