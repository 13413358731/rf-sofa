package com.realfinance.sofa.cg.core.service.evalformula;

import com.realfinance.sofa.cg.core.exception.RfCgCoreException;

public class EvalFormulaNotFoundException extends RfCgCoreException {
    public EvalFormulaNotFoundException() {
    }

    public EvalFormulaNotFoundException(String message) {
        super(message);
    }

    public EvalFormulaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvalFormulaNotFoundException(Throwable cause) {
        super(cause);
    }

    public EvalFormulaNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
