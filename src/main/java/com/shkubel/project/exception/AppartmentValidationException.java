package com.shkubel.project.exception;

public class AppartmentValidationException extends Exception {

    public AppartmentValidationException() {
        super();
    }

    public AppartmentValidationException(String message) {
        super(message);
    }

    public AppartmentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
