package com.realfinance.sofa.cg.security.sdebanksso;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

public class SsoAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private String getTokenUrl;
    private String getUserInfoUrl;
    private RestTemplate restTemplate;
    private UserDetailsService userDetailsService;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        SsoAuthenticationFilter authenticationFilter = new SsoAuthenticationFilter();

        // 设置AuthenticationManager
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        // 设置成功失败处理器
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        // 设置provider
        SsoAuthenticationProvider authenticationProvider = new SsoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setGetTokenUrl(getTokenUrl);
        authenticationProvider.setGetUserInfoUrl(getUserInfoUrl);
        authenticationProvider.setRestTemplate(restTemplate);
        // 配置顺序在UsernamePasswordAuthenticationFilter之前
        http.authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
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

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }
}
