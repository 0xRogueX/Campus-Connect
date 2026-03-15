package com.campusconnect.materialsservice.config.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI materialsServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CampusConnect Materials Service API")
                        .description("Upload, download and manage course materials")
                        .version("1.0.0")
                );
    }
}