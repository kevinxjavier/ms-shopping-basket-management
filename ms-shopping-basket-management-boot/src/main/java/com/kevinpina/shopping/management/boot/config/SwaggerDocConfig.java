package com.kevinpina.shopping.management.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Swagger settings.
 */
@Configuration
class SwaggerDocConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components()).info(new Info().title("APIRest - Shopping Basket Management API")
                .license(new License().name("Kevin Sweet Software - Enterprise")).version("1.0.0")
                .description("Below is a list of available REST API calls for this \"APIRest - Shopping Basket Management\""));
        
    }
}
