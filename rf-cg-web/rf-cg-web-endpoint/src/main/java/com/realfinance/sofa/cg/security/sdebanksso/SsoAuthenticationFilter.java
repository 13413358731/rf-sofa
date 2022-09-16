package com.realfinance.sofa.cg.security.sdebanksso;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SsoAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;


    public SsoAuthenticationFilter() {
        super(new AntPathRequestMatcher("/ssoLogin", "GET"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String code = this.obtainCode(request);
            if (code == null) {
                code = "";
            }
            String state = this.obtainState(request);
            if (state == null) {
                state = "";
            }
            SsoAuthenticationToken authRequest = new SsoAuthenticationToken(code, state);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }


    public boolean isPostOnly() {
        return postOnly;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    private String obtainCode(HttpServletRequest request) {
        return request.getParameter("code");
    }

    private String obtainState(HttpServletRequest request) {
        return request.getParameter("state");
    }

    private void setDetails(HttpServletRequest request, SsoAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
