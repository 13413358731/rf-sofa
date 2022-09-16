package com.realfinance.sofa.cg.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class UnauthorizedSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    public static final Logger log = LoggerFactory.getLogger(UnauthorizedSessionInformationExpiredStrategy.class);

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(401);
        response.getWriter().print(
                "{\"timestamp\": \"" + Calendar.getInstance().getTime() + "\",\"exception\": \"会话已过期\"}");
        response.flushBuffer();
    }
}
