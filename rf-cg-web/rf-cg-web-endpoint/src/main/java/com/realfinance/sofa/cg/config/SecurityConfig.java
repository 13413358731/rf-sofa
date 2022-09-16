package com.realfinance.sofa.cg.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinance.sofa.cg.security.AuthenticationFailureHandlerImpl;
import com.realfinance.sofa.cg.security.AuthenticationSuccessHandlerImpl;
import com.realfinance.sofa.cg.security.UnauthorizedSessionInformationExpiredStrategy;
import com.realfinance.sofa.cg.security.UserDetailsServiceImpl;
import com.realfinance.sofa.cg.security.sdebanksso.SsoAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SecurityConfig<S extends Session> extends WebSecurityConfigurerAdapter {

    private ObjectMapper objectMapper;
    private FindByIndexNameSessionRepository<S> sessionRepository;
    private String getTokenUrl;
    private String getUserInfoUrl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/cg/**/flow/callback").permitAll()
                .antMatchers(HttpMethod.GET,"/mytask/todotask/portalTaskVo").permitAll()
                .antMatchers("/ureport/**").permitAll()
                .antMatchers("/system/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(ssoAuthenticationSecurityConfig())
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
                .and()
                //.httpBasic().and()
                .formLogin()
                //.successForwardUrl("/swagger-ui/index.html")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and().logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .csrf().disable()
                .cors().and()
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), AnyRequestMatcher.INSTANCE)
                .and()
                .sessionManagement()
                .maximumSessions(5)
                .sessionRegistry(sessionRegistry())
                .expiredSessionStrategy(new UnauthorizedSessionInformationExpiredStrategy())
                .and()
                .sessionAuthenticationFailureHandler(authenticationFailureHandler());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui**/**", "/v3/api-docs/**", "/static/**", "/", "/index.html", "/logo.png", "/favicon.ico");
    }

    @Bean
    public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl(objectMapper);
    }


    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl(objectMapper);
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new HttpStatusReturningLogoutSuccessHandler();
    }

    @Bean
    public SsoAuthenticationSecurityConfig ssoAuthenticationSecurityConfig() {
        SsoAuthenticationSecurityConfig ssoAuthenticationSecurityConfig = new SsoAuthenticationSecurityConfig();
        ssoAuthenticationSecurityConfig.setGetTokenUrl(getTokenUrl);
        ssoAuthenticationSecurityConfig.setGetUserInfoUrl(getUserInfoUrl);
        ssoAuthenticationSecurityConfig.setRestTemplate(new RestTemplate());
        ssoAuthenticationSecurityConfig.setUserDetailsService(userDetailsService());
        ssoAuthenticationSecurityConfig.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        ssoAuthenticationSecurityConfig.setAuthenticationFailureHandler(authenticationFailureHandler());
        return ssoAuthenticationSecurityConfig;
    }

    @Value("${sdebank.sso.getTokenUrl}")
    public void setGetTokenUrl(String getTokenUrl) {
        this.getTokenUrl = getTokenUrl;
    }

    @Value("${sdebank.sso.getUserInfoUrl}")
    public void setGetUserInfoUrl(String getUserInfoUrl) {
        this.getUserInfoUrl = getUserInfoUrl;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setSessionRepository(FindByIndexNameSessionRepository<S> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // TODO 修改为更合理的配置
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // ureport
        source.registerCorsConfiguration("/ureport/**", configuration);
        return source;
    }
}
