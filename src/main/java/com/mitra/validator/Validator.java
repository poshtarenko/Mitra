package com.mitra.validator;

import com.mitra.dto.Dto;

public interface Validator<T extends Dto> {

    boolean isValid(T dto);

}
