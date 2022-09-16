package com.realfinance.sofa.flow;

import org.flowable.ui.common.security.FlowableUiSecurityAutoConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = FlowableUiSecurityAutoConfiguration.class)
//@ImportResource({"classpath*:sofa/ngdb_param_bpmc_server_provider.xml"})
public class FlowApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(FlowApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
