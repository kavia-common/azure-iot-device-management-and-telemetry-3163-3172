package com.example.deviceregistrationandtelemetrybackend.telemetry.api;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Telemetry event response DTO.
 */
@Schema(description = "Telemetry event for a device.")
public record TelemetryResponse(
        Long id,
        @Schema(example = "device-001") String deviceId,
        Instant timestamp,
        Double temperatureC,
        Double humidity,
        String rawJson
) { }
