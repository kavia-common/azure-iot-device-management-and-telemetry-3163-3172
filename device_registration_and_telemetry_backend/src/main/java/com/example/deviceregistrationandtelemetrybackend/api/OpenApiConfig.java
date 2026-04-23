package com.example.deviceregistrationandtelemetrybackend.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI metadata for Swagger UI.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Device Registration & Telemetry Backend",
                version = "0.1.0",
                description = "REST API for device registration and telemetry ingestion/retrieval. Integrates with Azure IoT Hub when configured, otherwise runs in local stub mode."
        ),
        tags = {
                @Tag(name = "Devices", description = "Device registration and retrieval"),
                @Tag(name = "Telemetry", description = "Telemetry ingestion and retrieval"),
                @Tag(name = "Hello Controller", description = "Basic endpoints for deviceregistrationandtelemetrybackend")
        }
)
public class OpenApiConfig {
}
