package com.mitra.exception;

public class EmptyParameterException extends RuntimeException {
    public EmptyParameterException() {
    }

    public EmptyParameterException(String message) {
        super(message);
    }
}
