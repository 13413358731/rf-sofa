package com.realfinance.sofa.cg.aspect;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.realfinance.sofa.cg.util.RpcUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 添加RPC上下文
 */
@Aspect
@Component
public class SofaRpcAspect {

    private final static Logger log = LoggerFactory.getLogger(SofaRpcAspect.class);

    @Pointcut("execution(* com.realfinance.sofa.cg.controller..*(..))")
    public void putRequestBaggage(){}

    @Before("putRequestBaggage()")
    public void before(){
        try {
            RpcUtils.putRequestBaggage(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception ignored) {
        }
    }

    @After("putRequestBaggage()")
    public void after(){
        RpcInvokeContext.removeContext();
    }

}
