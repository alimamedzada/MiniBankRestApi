package com.company.MiniBankByUsingSpring.exception;

public class InputValidationException extends BankException {
    public InputValidationException(String message) {
        super(message);
    }

    public InputValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
