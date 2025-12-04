package com.Project.UPIRecon.ingest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ingestApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ingest Service API")
                        .description("APIs for ingesting raw UPI transactions via JSON and Excel")
                        .version("1.0"));
    }
}
