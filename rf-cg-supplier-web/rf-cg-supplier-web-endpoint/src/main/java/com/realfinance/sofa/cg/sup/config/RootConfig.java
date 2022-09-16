package com.realfinance.sofa.cg.sup.config;

import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filestore.impl.LocalFileStore;
import com.realfinance.sofa.common.notice.sms.SmsSender;
import com.realfinance.sofa.common.notice.sms.impl.ConsoleSmsSender;
import com.realfinance.sofa.common.ocr.NotSupportedOcrService;
import com.realfinance.sofa.common.ocr.OcrService;
import com.realfinance.sofa.common.util.SpringContextHolder;
import com.realfinance.sofa.sdebank.SdebankSmsSender;
import com.realfinance.sofa.sdebank.SdebankZScanApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RootConfig {

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    @Profile({"dev","uat"})
    public SmsSender consoleSmsSender() {
        return new ConsoleSmsSender();
    }

    @Bean
    @Profile("sdebanksit")
    public SmsSender sdebankSmsSender() {
        return new SdebankSmsSender("172.16.249.81",10029);
    }

    @Bean
    @Profile({"dev","uat"})
    public FileStore localFileStore() {
        return new LocalFileStore();
    }

    @Bean
    @Profile({"dev","uat"})
    public OcrService notSupportedOcrService() {
        return new NotSupportedOcrService();
    }

    @Bean
    @Profile("sdebanksit")
    public SdebankZScanApp sdebankZScanApp() {
        return new SdebankZScanApp();
    }
}
