package com.mitra.validator;

import com.mitra.dto.Dto;

public interface Validator<T extends Dto> {

    /**
     * Checks DTO is valid
     *
     * @param dto DTO
     * @return true if DTO is valid, false if DTO is invalid
     */
    boolean isValid(T dto);

}
