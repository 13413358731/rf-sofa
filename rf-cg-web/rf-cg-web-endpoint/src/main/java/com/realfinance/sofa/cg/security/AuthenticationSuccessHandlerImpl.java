package com.realfinance.sofa.cg.security;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.realfinance.sofa.flow.facade.TaskFacade;
import com.realfinance.sofa.flow.model.TaskDto;
import com.realfinance.sofa.flow.model.TaskQueryCriteria;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证成功处理
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    public static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    @SofaReference(interfaceType = TaskFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private TaskFacade taskFacade;

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    private final ObjectWriter objectWriter;

    public AuthenticationSuccessHandlerImpl(ObjectMapper objectMapper) {
        this.objectWriter = objectMapper.writerFor(Response.class);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("认证成功: {}", authentication);
        }

        String state = StringUtils.substringAfterLast(httpServletRequest.getQueryString(), "state=");
        if (httpServletRequest.getRequestURI().equals("/ssoLogin") && state.equals("348")) {
            Pageable pageable = PageRequest.of(0, 5);
            TaskQueryCriteria queryCriteria = new TaskQueryCriteria();
            String departmentCode = "D_" + systemQueryFacade.findDepartmentCodeToUserId(Integer.parseInt(authentication.getName()));
            //获取登录人所有角色code
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> groupIn = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(e -> e.startsWith("ROLE_"))
                    .map(e -> "R_" + e.substring("ROLE_".length()))
                    .collect(Collectors.toList());
            groupIn.add(departmentCode);
            queryCriteria.setCandidateGroupIn(groupIn);
            queryCriteria.setCandidateOrAssigned(authentication.getName());
            Page<TaskDto> result = taskFacade.list(queryCriteria, pageable);
            long totalElements = result.getTotalElements();
            httpServletResponse.sendRedirect("/mytask/todotask/portalTaskVo?task="+totalElements);
        } else {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            try (OutputStream out = httpServletResponse.getOutputStream()) {
                objectWriter.writeValue(out, new Response(Calendar.getInstance().getTime(), httpServletRequest.getSession().getId()));
            }
        }
    }

    public static class Response {
        private Date timestamp;
        private String sessionId;

        public Response() {
        }

        public Response(Date timestamp, String sessionId) {
            this.timestamp = timestamp;
            this.sessionId = sessionId;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }
}
