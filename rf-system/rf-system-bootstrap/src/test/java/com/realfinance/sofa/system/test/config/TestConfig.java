package com.realfinance.sofa.system.test.config;

import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public AbstractTestBase.TestHelper testHelper() {
        return new AbstractTestBase.TestHelper();
    }
}
