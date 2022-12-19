package com.mitra.exception;

public class PageNotFoundException extends RuntimeException {

    public PageNotFoundException() {
    }

    public PageNotFoundException(String message) {
        super(message);
    }
}
