package com.realfinance.sofa.cg.sup.security.decrypt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class DecryptAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private AuthenticationFailureHandler authenticationFailureHandler;
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        DecryptUsernamePasswordAuthenticationFilter authenticationFilter = new DecryptUsernamePasswordAuthenticationFilter();

        // 设置AuthenticationManager
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        // 设置成功失败处理器
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        // 配置顺序在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }
}
