package com.company.MiniBankByUsingSpring.accounts.exception;

public class InvalidInterestException extends RuntimeException {

    public InvalidInterestException(String message) {
        super(message);
    }

    public InvalidInterestException(String message, Throwable cause) {
        super(message, cause);
    }
}
