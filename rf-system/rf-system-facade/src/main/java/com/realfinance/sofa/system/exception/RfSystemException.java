package com.realfinance.sofa.system.exception;

public class RfSystemException extends RuntimeException {

    public RfSystemException() {
    }

    public RfSystemException(String message) {
        super(message);
    }

    public RfSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public RfSystemException(Throwable cause) {
        super(cause);
    }

    public RfSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
