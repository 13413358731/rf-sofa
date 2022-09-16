package com.realfinance.sofa.flow.exception;

public class RfFlowException extends RuntimeException {

    public RfFlowException() {
    }

    public RfFlowException(String message) {
        super(message);
    }

    public RfFlowException(String message, Throwable cause) {
        super(message, cause);
    }

    public RfFlowException(Throwable cause) {
        super(cause);
    }

    public RfFlowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
