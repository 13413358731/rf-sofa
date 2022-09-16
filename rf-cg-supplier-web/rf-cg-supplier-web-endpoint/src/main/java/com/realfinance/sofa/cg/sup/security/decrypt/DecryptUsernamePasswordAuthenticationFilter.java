package com.realfinance.sofa.cg.sup.security.decrypt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinance.sofa.cg.sup.util.ApiSecureUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 加密登录过滤器
 */
public class DecryptUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = true;

    private ObjectMapper objectMapper = new ObjectMapper();

    // ~ Constructors
    // ===================================================================================================

    public DecryptUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/elogin", "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String data = request.getParameter("data");
        if (data == null || data.isEmpty()) {
            throw new AuthenticationServiceException(
                    "Missing data");
        }

        byte[] decrypt;
        try {
            decrypt = ApiSecureUtils.decrypt(data);
        } catch (Exception e) {
            throw new AuthenticationServiceException(
                    "Data error");
        }

        String username;
        String password;
        try {
            JsonNode jsonNode = objectMapper.readTree(decrypt);
            username = jsonNode.get(usernameParameter).asText();
            password = jsonNode.get(passwordParameter).asText();
        } catch (IOException e) {
            throw new AuthenticationServiceException(
                    "Obtain username or password error");
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request,
                              UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
