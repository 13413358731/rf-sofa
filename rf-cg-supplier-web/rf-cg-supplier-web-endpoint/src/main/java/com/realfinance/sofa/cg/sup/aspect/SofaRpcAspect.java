package com.realfinance.sofa.cg.sup.aspect;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 添加RPC上下文
 */
@Aspect
@Component
public class SofaRpcAspect {

    private final static Logger log = LoggerFactory.getLogger(SofaRpcAspect.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Pointcut("execution(* com.realfinance.sofa.cg.sup.controller..*(..))")
    public void putRequestBaggage(){}

    @Before("putRequestBaggage()")
    public void before(){
        try {
            putRequestBaggage(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception ignored) {
        }
    }

    @After("putRequestBaggage()")
    public void after(){
        RpcInvokeContext.removeContext();
    }

    private void putRequestBaggage(Authentication authentication) {
        if (log.isTraceEnabled()) {
            log.trace("设置安全认证到RPC上下文：{}", authentication);
        }
        if (authentication.isAuthenticated()) {
            RpcUtils.setPrincipalId(0);
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null) {
                RpcUtils.setTenantId((String) session.getAttribute("tenant"));
            }
        }
    }
}
