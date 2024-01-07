package com.bookstore.bookstore.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final String API_KEY = "Bearer Token";

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(
                new Info()
                        .title("Book store Spring API")
                        .version("1.0")
                        .description("Documentation Project Book store Spring API v1.0")
        );
        openAPI.components(
                new Components()
                        .addSecuritySchemes(
                                API_KEY,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .scheme("Bearer")
                                        .bearerFormat("JWT")
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                        )
        );
        openAPI.addSecurityItem(new SecurityRequirement().addList(API_KEY));
        return openAPI;
    }
}
