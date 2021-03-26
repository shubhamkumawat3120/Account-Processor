package com.account.processor.exception;

public class AccountNotExistException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AccountNotExistException(String message) {
        super(message);
    }

    public AccountNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
