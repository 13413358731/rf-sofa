package com.realfinance.sofa.cg.sup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ImportResource({"classpath*:sofa/ngdb_param_bpmc_server_provider.xml"})
public class CgSupplierApplication {
    public static void main(String[] args) {
        SpringApplication.run(CgSupplierApplication.class, args);
    }
}
