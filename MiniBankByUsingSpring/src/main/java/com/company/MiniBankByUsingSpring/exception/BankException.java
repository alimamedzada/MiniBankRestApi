package com.company.MiniBankByUsingSpring.exception;


public abstract class BankException extends RuntimeException {
    public BankException(String msg) {
        super(msg);
    }

    public BankException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
