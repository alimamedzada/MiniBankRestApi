package com.company.MiniBankByUsingSpring.exception;

public class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
