package com.company.MiniBankByUsingSpring.accounts.exception;

public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String msg) {
        super(msg);
    }

    public InvalidAccountException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
