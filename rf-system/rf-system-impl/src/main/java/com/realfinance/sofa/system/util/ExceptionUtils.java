package com.realfinance.sofa.system.util;

import com.realfinance.sofa.common.datascope.DataAccessForbiddenException;
import com.realfinance.sofa.system.exception.RfSystemException;

public class ExceptionUtils {

    public static RuntimeException entityNotFound(Class<?> entityClass, String attr, Object value) {
        return new RfSystemException(String.format("%s(%s:%s)不存在", entityClass.getName(),attr, value));
    }

    public static RuntimeException dataAccessForbidden() {
        return new DataAccessForbiddenException("无权访问");
    }

    public static RuntimeException businessException(String msg) {
        throw new RfSystemException(msg);
    }

    public static RuntimeException businessException(String msg, Throwable e) {
        throw new RfSystemException(msg, e);
    }
}
