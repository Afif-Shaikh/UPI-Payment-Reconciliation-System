package com.Project.UPIRecon.recon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
    public OpenAPI reconApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Recon Service API")
                        .description("APIs for querying reconciliation logic via JSON and Excel")
                        .version("1.0"));
    }
}
