package com.realfinance.sofa.cg.sup.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证成功处理
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    public static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    private final ObjectMapper objectMapper;

    public AuthenticationSuccessHandlerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("认证成功: {}",authentication);
        }

        httpServletRequest.getSession(true).setAttribute("tenant",obtainTenantId());

        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> data = new HashMap<>();
        data.put(
                "timestamp",
                Calendar.getInstance().getTime());
        data.put(
                "sessionId",
                httpServletRequest.getSession().getId());
        try (OutputStream out = httpServletResponse.getOutputStream()) {
            objectMapper.writeValue(out,data);
        }
    }

    /**
     * 从请求中获取租户ID
     * @return
     */
    private String obtainTenantId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            if (log.isWarnEnabled()) {
                log.warn("RequestAttributes is null");
            }
            throw new RuntimeException("RequestAttributes is null");
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String tenantId = request.getParameter("tenant");
            if (log.isTraceEnabled()) {
                log.trace("从请求中获取租户ID：{}",tenantId);
            }
            if (tenantId == null) {
                throw new RuntimeException("Tenant is missing");
            }
            return tenantId;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("获取租户异常",e);
            }
            throw new RuntimeException("Tenant is missing");
        }
    }
}
