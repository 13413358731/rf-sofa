package com.realfinance.sofa.cg.sup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinance.sofa.cg.sup.security.AuthenticationFailureHandlerImpl;
import com.realfinance.sofa.cg.sup.security.AuthenticationSuccessHandlerImpl;
import com.realfinance.sofa.cg.sup.security.UnauthorizedSessionInformationExpiredStrategy;
import com.realfinance.sofa.cg.sup.security.UserDetailsServiceImpl;
import com.realfinance.sofa.cg.sup.security.decrypt.DecryptAuthenticationSecurityConfig;
import com.realfinance.sofa.cg.sup.security.decrypt.DecryptUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/cg/sup/account/**")
                .permitAll().antMatchers("/cg/supAnnoucemt/**")
                .permitAll().antMatchers("/cg/supsolicitation/**")
                .permitAll().antMatchers("/file/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
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
                .apply(decryptAuthenticationSecurityConfig())
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredSessionStrategy(new UnauthorizedSessionInformationExpiredStrategy())
                .and()
                .sessionAuthenticationFailureHandler(authenticationFailureHandler())
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui**/**","/v3/api-docs/**","/static/**","/","/index.html","/logo.png","/favicon.ico");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean("myAuthenticationManagerBean")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DecryptAuthenticationSecurityConfig decryptAuthenticationSecurityConfig() {
        DecryptAuthenticationSecurityConfig ssoAuthenticationSecurityConfig = new DecryptAuthenticationSecurityConfig();
        ssoAuthenticationSecurityConfig.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        ssoAuthenticationSecurityConfig.setAuthenticationFailureHandler(authenticationFailureHandler());
        return ssoAuthenticationSecurityConfig;
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
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /*@Autowired
    FindByIndexNameSessionRepository<?> findByIndexNameSessionRepository;

    @Bean
    public SessionRegistry sessionRegistry(){
        SessionRegistry sessionRegistry = new SpringSessionBackedSessionRegistry(findByIndexNameSessionRepository);
        return sessionRegistry;
    }*/



    // 注入环境中的objectMapper
    @Autowired
    ObjectMapper objectMapper;
}
