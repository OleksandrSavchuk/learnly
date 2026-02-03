package com.example.learnly.exception;

public class InvalidApiRequestException extends RuntimeException {
    public InvalidApiRequestException() {
    }
    public InvalidApiRequestException(String message) {
        super(message);
    }
    public InvalidApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidApiRequestException(Throwable cause) {
        super(cause);
    }
    public InvalidApiRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
