package com.account.processor.exception;

public class InvalidAddressException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidAddressException(String message) {
        super(message);
    }

    public InvalidAddressException(String message, Throwable cause) {
        super(message, cause);
    }
}
