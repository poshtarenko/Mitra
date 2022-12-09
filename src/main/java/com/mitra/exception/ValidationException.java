package com.mitra.exception;

import com.mitra.validator.Error;

import java.util.List;

public class ValidationException extends Exception {

    List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
