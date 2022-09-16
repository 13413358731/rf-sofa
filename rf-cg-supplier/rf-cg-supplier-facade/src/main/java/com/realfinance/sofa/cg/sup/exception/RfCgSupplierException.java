package com.realfinance.sofa.cg.sup.exception;

public class RfCgSupplierException extends RuntimeException {

    public RfCgSupplierException() {
    }

    public RfCgSupplierException(String message) {
        super(message);
    }

    public RfCgSupplierException(String message, Throwable cause) {
        super(message, cause);
    }

    public RfCgSupplierException(Throwable cause) {
        super(cause);
    }

    public RfCgSupplierException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
