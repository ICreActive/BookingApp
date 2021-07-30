package com.shkubel.project.exception;

public class DateValidationException extends Exception {

    public DateValidationException() {
        super();
    }

    public DateValidationException(String message) {
        super(message);
    }

    public DateValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
