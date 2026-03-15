package com.campusconnect.profileservice.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Centralized OpenAPI & Swagger UI configuration.
 * Simply include this JAR in any service to enable /swagger-ui.html.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI campusConnectOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CampusConnect API Documentation")
                        .description("Interactive API docs for all CampusConnect microservices")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("CampusConnect Team")
                                .email("support@campusconnect.com")
                        )
                );
    }

    @Bean
    public GroupedOpenApi allServicesApi() {
        return GroupedOpenApi.builder()
                .group("campusconnect-all")
                .pathsToMatch("/**")
                .build();
    }
}
