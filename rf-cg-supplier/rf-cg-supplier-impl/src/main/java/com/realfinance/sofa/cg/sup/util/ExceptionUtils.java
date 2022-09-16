package com.realfinance.sofa.cg.sup.util;

import com.realfinance.sofa.cg.sup.exception.RfCgSupplierException;
import com.realfinance.sofa.common.datascope.DataAccessForbiddenException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ExceptionUtils {

    public static RuntimeException entityNotFound(Class<?> entityClass, String attr, Object value) {
        return new RfCgSupplierException(String.format("%s(%s:%s)不存在", entityClass.getName(),attr, value));
    }

    public static RuntimeException supplierNotFound(Class<?> entityClass, String attr, Object value) {
        return new UsernameNotFoundException(String.format("%s(%s:%s)不存在", entityClass.getName(),attr, value));
    }

    public static RuntimeException dataAccessForbidden() {
        return new DataAccessForbiddenException("无权访问");
    }

    public static RuntimeException businessException(String msg) {
        throw new RfCgSupplierException(msg);
    }

    public static RuntimeException businessException(String msg, Throwable e) {
        throw new RfCgSupplierException(msg,e);
    }
}
