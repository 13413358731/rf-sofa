package com.realfinance.sofa.cg.security.sdebanksso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

public class SsoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(SsoAuthenticationProvider.class);

    private UserDetailsService userDetailsService;

    private String getTokenUrl;
    private String getUserInfoUrl;

    private RestTemplate restTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SsoAuthenticationToken ssoAuthenticationToken = ((SsoAuthenticationToken) authentication);
        return super.authenticate(new UsernamePasswordAuthenticationToken(ssoAuthenticationToken.getPrincipal(), ssoAuthenticationToken.getState()));
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (log.isTraceEnabled()) {
            log.trace("Retrieve user, code: {}", username);
        }
        String token = getToken(username);
        return userDetailsService.loadUserByUsername(getUsername(token));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 忽略了密码校验
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SsoAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setGetTokenUrl(String getTokenUrl) {
        this.getTokenUrl = getTokenUrl;
    }

    public void setGetUserInfoUrl(String getUserInfoUrl) {
        this.getUserInfoUrl = getUserInfoUrl;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected String getToken(String code) {
        try {
            ResponseEntity<GetTokenResponse> getTokenResponse = restTemplate
                    .postForEntity(MessageFormat.format(getTokenUrl, code), null, GetTokenResponse.class);
            if (getTokenResponse.getStatusCode() == HttpStatus.OK) {
                GetTokenResponse tokenBody = getTokenResponse.getBody();
                if (tokenBody == null) {
                    throw new AuthenticationServiceException("访问获取授权Token接口成功，但缺少响应体");
                }
                return tokenBody.getAccess_token();
            }
        } catch (RestClientException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        throw new AuthenticationServiceException("获取Token失败");
    }

    protected String getUsername(String token) {
        try {
            ResponseEntity<GetUserInfoResponse> getUserInfoResponse = restTemplate
                    .getForEntity(MessageFormat.format(getUserInfoUrl, token), GetUserInfoResponse.class);
            if (getUserInfoResponse.getStatusCode() == HttpStatus.OK) {
                GetUserInfoResponse userInfoBody = getUserInfoResponse.getBody();
                if (userInfoBody.getSpRoleList() == null || userInfoBody.getSpRoleList().size()==0){
                    throw new AuthenticationServiceException("获取用户名失败");
                }
                return userInfoBody.getSpRoleList().get(0);
            }
        } catch (RestClientException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        throw new AuthenticationServiceException("获取用户名失败");
    }
}
