package com.Project.UPIRecon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "UPI Recon Ingest API",
        version = "1.0",
        description = "API for ingesting UPI raw transactions"
    )
)

@SpringBootApplication
public class UpiReconApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpiReconApplication.class, args);
	}

}
