package com.realfinance.sofa.cg.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
//@ImportResource({"classpath*:sofa/ngdb_param_bpmc_server_provider.xml"})
public class CgCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CgCoreApplication.class, args);
    }
}
