package com.company.MiniBankByUsingSpring.exception;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException(String message, Throwable cause) {
        super(message);
    }

    public InvalidAuthenticationException(String message) {
        super(message);
    }
}
