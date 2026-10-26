package com.uniti.cil.itta.inventory.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI inventoryOpenApi(@Value("${spring.application.name}") String name) {
        return new OpenAPI().info(new Info().title(name).version("v1")
                .description("Uniti Common Integration Layer - ITTA network inventory"));
    }
}
