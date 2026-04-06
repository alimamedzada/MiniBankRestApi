package com.company.MiniBankByUsingSpring.exception;

public class InvalidInterestException extends BankException {

    public InvalidInterestException(String message) {
        super(message);
    }

    public InvalidInterestException(String message, Throwable cause) {
        super(message, cause);
    }
}
