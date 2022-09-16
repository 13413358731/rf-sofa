package com.realfinance.sofa.common.datascope;

public class DataAccessForbiddenException extends RuntimeException {

    public DataAccessForbiddenException() {
    }

    public DataAccessForbiddenException(String message) {
        super(message);
    }

    public DataAccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public DataAccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
