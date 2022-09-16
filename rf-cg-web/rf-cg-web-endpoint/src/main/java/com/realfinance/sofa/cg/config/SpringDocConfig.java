package com.realfinance.sofa.cg.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocUtils;
import org.springdoc.core.converters.models.DefaultPageable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    static {
        SpringDocUtils.getConfig()
                .replaceWithClass(org.springframework.data.domain.Pageable.class, DefaultPageable.class);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("basicScheme"))
                .components(new Components()
                        .addSecuritySchemes("basicScheme", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic"))
                )
                .info(new Info()
                        .title("采购系统接口文档")
                        .description("更多请咨询服务开发者 realfinance")
                        .version("beta")
                        .contact(new Contact()
                                .name("realfinance")
                                .email("service@realfinance.com.cn")
                                .url("http://www.realfinance.com.cn")
                        ));
    }

    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder()
                .group("系统模块")
                .packagesToScan("com.realfinance.sofa.cg.controller.system")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public GroupedOpenApi flowApi() {
        return GroupedOpenApi.builder()
                .group("流程模块")
                .packagesToScan("com.realfinance.sofa.cg.controller.flow")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi cgApi() {
        return GroupedOpenApi.builder()
                .group("采购模块")
                .packagesToScan("com.realfinance.sofa.cg.controller.cg")
                .pathsToMatch("/**")
                .build();
    }

}
