package com.realfinance.sofa.cg.config;

import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filestore.impl.LocalFileStore;
import com.realfinance.sofa.common.util.SpringContextHolder;
import com.realfinance.sofa.sdebank.SdebankZScanApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class RootConfig {

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    @Profile({"dev","uat"})
    public FileStore fileStore() {
        return new LocalFileStore();
    }

    @Bean
    @Profile("sdebanksit")
    public SdebankZScanApp sdebankZScanApp() {
        return new SdebankZScanApp();
    }
}
