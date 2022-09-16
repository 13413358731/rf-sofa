package com.realfinance.sofa.cg.sup.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    public static final Logger log = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);

    private final ObjectMapper objectMapper;

    public AuthenticationFailureHandlerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("认证失败: {}",exception.getMessage());
        }
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(401);
        Map<String, Object> data = new HashMap<>();
        data.put(
                "timestamp",
                Calendar.getInstance().getTime());
        data.put(
                "exception",
                exception.getMessage());
        try (OutputStream out = response.getOutputStream()) {
            objectMapper.writeValue(out,data);
        }
    }



}
