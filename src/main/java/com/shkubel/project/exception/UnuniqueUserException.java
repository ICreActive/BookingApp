package com.shkubel.project.exception;

public class UnuniqueUserException extends Exception{

    public UnuniqueUserException() {
        super();
    }

    public UnuniqueUserException(String message) {
        super(message);
    }
}
