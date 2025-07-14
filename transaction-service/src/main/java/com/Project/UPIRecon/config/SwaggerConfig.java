package com.Project.UPIRecon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI upiReconAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UPI Recon API")
                        .description("API for ingesting and querying UPI transaction data")
                        .version("1.0"));
    }
}
