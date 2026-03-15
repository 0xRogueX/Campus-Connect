package com.campusconnect.authservice.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI campusConnectOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info()
                        .title("CampusConnect Auth Service API")
                        .description("Interactive API docs for CampusConnect Auth Service")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("CampusConnect Team")
                                .email("support@campusconnect.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        )
                );
    }

    @Bean
    public GroupedOpenApi authServiceApiGroup() {
        return GroupedOpenApi.builder()
                .group("campusconnect-auth")
                .pathsToMatch("/api/auth/**")
                .build();
    }
}

/*

Purpose	URL
Swagger UI	http://localhost:8081/auth/swagger-ui/index.html
OpenAPI JSON spec	http://localhost:8081/auth/v3/api-docs


1. Register
POST /api/auth/register
Content-Type: application/json
json
{
  "email": "john.doe@example.com",
  "password": "Secret#123",        // must be at least 6 chars
  "role": "student"                // e.g. "student", "faculty", "admin"
}

2. Verify OTP
POST /api/auth/verify-otp
Content-Type: application/json
json
{
  "email": "john.doe@example.com",
  "otp": "123456"
}

3. Login
POST /api/auth/login
Content-Type: application/json
json
{
  "email": "john.doe@example.com",
  "password": "Secret#123"
}

 */