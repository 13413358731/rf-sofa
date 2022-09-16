package com.realfinance.sofa.cg.core.util;

import com.realfinance.sofa.cg.core.exception.RfCgCoreException;
import com.realfinance.sofa.common.datascope.DataAccessForbiddenException;

public class ExceptionUtils {

    public static RuntimeException entityNotFound(Class<?> entityClass, String attr, Object value) {
        return new RfCgCoreException(String.format("%s(%s:%s)不存在", entityClass.getName(),attr, value));
    }

    public static RuntimeException dataAccessForbidden() {
        return new DataAccessForbiddenException("无权访问");
    }

    public static RuntimeException businessException(String msg) {
        throw new RfCgCoreException(msg);
    }

    public static RuntimeException businessException(String msg, Throwable e) {
        throw new RfCgCoreException(msg,e);
    }
}
