package com.company.MiniBankByUsingSpring.exception;

public class InvalidAccountException extends BankException {
    public InvalidAccountException(String msg) {
        super(msg);
    }

    public InvalidAccountException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
