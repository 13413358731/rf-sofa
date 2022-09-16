package com.realfinance.sofa.flow.util;

import com.realfinance.sofa.flow.exception.RfFlowException;

public class ExceptionUtils {

    public static RuntimeException entityNotFound(Class<?> entityClass, String attr, Object value) {
        return new RfFlowException(String.format("%s(%s:%s)不存在", entityClass.getName(),attr, value));
    }

    public static RuntimeException businessException(String msg) {
        throw new RfFlowException(msg);
    }

    public static RuntimeException businessException(String msg, Throwable e) {
        throw new RfFlowException(msg, e);
    }
}
