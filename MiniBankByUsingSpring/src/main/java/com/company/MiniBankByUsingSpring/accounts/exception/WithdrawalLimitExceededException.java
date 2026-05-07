package com.company.MiniBankByUsingSpring.accounts.exception;

public class WithdrawalLimitExceededException extends RuntimeException {
    public WithdrawalLimitExceededException(String message) {
        super(message);
    }

    public WithdrawalLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
