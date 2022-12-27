package com.mitra.exception;

import com.mitra.validator.Error;

import java.util.List;

public class ValidationException extends RuntimeException {

    List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
