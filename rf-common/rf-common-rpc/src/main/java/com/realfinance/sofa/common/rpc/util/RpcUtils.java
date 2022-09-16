package com.realfinance.sofa.common.rpc.util;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.realfinance.sofa.common.rpc.constants.RpcContextConstants;
import com.realfinance.sofa.common.rpc.exception.RpcInvokeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class RpcUtils implements RpcContextConstants {

    private static final Logger log = LoggerFactory.getLogger(RpcUtils.class);

    /** 代替{@code null}传入RPC上下文 */
    private static final Object NULL_OBJECT = new Object();

    public static void put(String key, Object value) {
        RpcInvokeContext.getContext().put(key, value);
    }

    public static Object get(String key) {
        return RpcInvokeContext.getContext().get(key);
    }

    public static <T> T get(String key, Class<T> clazz) {
        Object value = get(key);
        if (value == null) {
            return null;
        }
        return clazz.cast(value);
    }

    /**
     * 从RPC上下文中获取
     * @param key 键
     * @param supplier 上下文中不存在则调用此参数的get方法获取
     * @param <T>
     * @return
     */
    public static <T> T getCache(String key, Supplier<T> supplier) {
        Assert.notNull(key,"Key can not be null");
        Assert.notNull(supplier,"Supplier can not be null");
        RpcInvokeContext context = RpcInvokeContext.getContext();
        Object o = context.get(key);
        if (o == null) {
            try {
                o = supplier.get();
                context.put(key,o == null ? NULL_OBJECT : o);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("supplier.get()，初次获取失败，本次请求将忽略key为{}的获取",key);
                    log.error("",e);
                }
                // supplier报错将在本次请求中忽略此Key
                context.put(key,NULL_OBJECT);
            }
        }
        return o == NULL_OBJECT ? null : (T) o;
    }

    public static void putRequestBaggage(String key, String value) {
        RpcInvokeContext.getContext().putRequestBaggage(key,value);
    }

    public static void setPrincipalId(Integer principalId) {
        RpcInvokeContext.getContext().putRequestBaggage(PRINCIPAL_ID_REQUEST_BAGGAGE_KEY, Objects.requireNonNull(principalId).toString());
    }

    public static Optional<Integer> getPrincipalId() {
        try {
            return getRequestBaggage(PRINCIPAL_ID_REQUEST_BAGGAGE_KEY).map(Integer::valueOf);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Integer requirePrincipalId() {
        return getPrincipalId().orElseThrow(() -> requireRequestBaggageException(PRINCIPAL_ID_REQUEST_BAGGAGE_KEY));
    }

    public static void setPrincipalDepartmentId(Integer principalDepartmentId) {
        RpcInvokeContext.getContext().putRequestBaggage(PRINCIPAL_DEPARTMENT_ID_REQUEST_BAGGAGE_KEY, Objects.requireNonNull(principalDepartmentId).toString());
    }

    public static Optional<Integer> getPrincipalDepartmentId() {
        try {
            return getRequestBaggage(PRINCIPAL_DEPARTMENT_ID_REQUEST_BAGGAGE_KEY).map(Integer::valueOf);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static void setPrincipalDepartmentCode(String principalDepartmentCode) {
        RpcInvokeContext.getContext().putRequestBaggage(PRINCIPAL_DEPARTMENT_CODE_REQUEST_BAGGAGE_KEY, Objects.requireNonNull(principalDepartmentCode));
    }

    public static Optional<String> getPrincipalDepartmentCode() {
        try {
            return getRequestBaggage(PRINCIPAL_DEPARTMENT_CODE_REQUEST_BAGGAGE_KEY).map(String::valueOf);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Integer requirePrincipalDepartmentId() {
        return getPrincipalDepartmentId().orElseThrow(() -> requireRequestBaggageException(PRINCIPAL_DEPARTMENT_ID_REQUEST_BAGGAGE_KEY));
    }

    public static void setTenantId(String tenantId) {
        RpcInvokeContext.getContext().putRequestBaggage(TENANT_ID_REQUEST_BAGGAGE_KEY, tenantId);
    }

    public static Optional<String> getTenantId() {
        try {
            return getRequestBaggage(TENANT_ID_REQUEST_BAGGAGE_KEY);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static String requireTenantId() {
        return getTenantId().orElseThrow(() -> requireRequestBaggageException(TENANT_ID_REQUEST_BAGGAGE_KEY));
    }

    public static Optional<String> getRequestBaggage(String key) {
        return Optional.ofNullable(RpcInvokeContext.getContext().getRequestBaggage(key));
    }

    public static String requireRequestBaggage(String key) {
        return getRequestBaggage(key).orElseThrow(() -> requireRequestBaggageException(key));
    }

    public static void removeRequestBaggage(String key) {
        RpcInvokeContext.getContext().removeRequestBaggage(key);
    }

    public static void removeRpcContext() {
        RpcInvokeContext.removeContext();
    }

    private static RpcInvokeException requireRequestBaggageException(String key) {
        return new RpcInvokeException("Request baggage [" + key + "] is required");
    }

}

