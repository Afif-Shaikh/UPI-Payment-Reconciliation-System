package com.Project.UPIRecon.normalizer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI normalizerApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Normalizer Service API")
                        .description("APIs for normalizing raw UPI transactions via JSON and Excel")
                        .version("1.0"));
    }
}
