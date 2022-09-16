package com.realfinance.sofa.cg.core.exception;

public class RfCgCoreException extends RuntimeException {

    public RfCgCoreException() {
    }

    public RfCgCoreException(String message) {
        super(message);
    }

    public RfCgCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public RfCgCoreException(Throwable cause) {
        super(cause);
    }

    public RfCgCoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
