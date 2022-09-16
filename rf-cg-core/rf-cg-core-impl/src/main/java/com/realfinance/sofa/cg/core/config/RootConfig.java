package com.realfinance.sofa.cg.core.config;

import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import com.realfinance.sofa.common.jpa.auditor.SofaRpcAuditor;
import com.realfinance.sofa.common.notice.sms.SmsSender;
import com.realfinance.sofa.common.util.SpringContextHolder;
import com.realfinance.sofa.sdebank.SdebankSmsSender;
import org.springframework.beans.factory.annotation.Value;
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
    public JpaQueryHelper jpaQueryHelper() {
        return new JpaQueryHelper();
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public SmsSender smsSender(@Value("${sdebank.sms.host:'172.16.249.81'}") String sdebankSmsHost,
                               @Value("${sdebank.sms.port:10029}") Integer sdebankSmsPort) {
        SdebankSmsSender smsSender = new SdebankSmsSender(sdebankSmsHost,sdebankSmsPort);
        return smsSender;
    }

}
