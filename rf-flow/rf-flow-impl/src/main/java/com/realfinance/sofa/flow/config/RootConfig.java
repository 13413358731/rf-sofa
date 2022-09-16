package com.realfinance.sofa.flow.config;


import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import com.realfinance.sofa.common.jpa.auditor.SofaRpcAuditor;
import com.realfinance.sofa.common.util.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@EnableJpaAuditing
public class RootConfig {
    @Bean
    public AuditorAware<Integer> auditor() {
        return new SofaRpcAuditor();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public JpaQueryHelper queryHelper() {
        return new JpaQueryHelper();
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
