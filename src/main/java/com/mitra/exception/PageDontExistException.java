package com.mitra.exception;

public class PageDontExistException extends RuntimeException {
    public PageDontExistException() {
    }

    public PageDontExistException(String message) {
        super(message);
    }

    public PageDontExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageDontExistException(Throwable cause) {
        super(cause);
    }

    public PageDontExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
