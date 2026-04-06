package com.company.MiniBankByUsingSpring.exception;

public class InsufficientBalanceException extends BankException {
    public InsufficientBalanceException(String message) {
        super(message);
    }

    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
