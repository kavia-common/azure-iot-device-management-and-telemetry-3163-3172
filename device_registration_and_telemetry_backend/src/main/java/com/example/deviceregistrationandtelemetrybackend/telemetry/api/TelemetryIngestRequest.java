package com.example.deviceregistrationandtelemetrybackend.telemetry.api;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Telemetry ingestion request.
 */
@Schema(description = "Telemetry payload to ingest for a device.")
public class TelemetryIngestRequest {

    @NotNull
    @Schema(description = "Event timestamp in ISO-8601. If not provided by client, use current time on client side.",
            example = "2026-01-01T12:00:00Z",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant timestamp;

    @Schema(description = "Temperature in Celsius.", example = "22.5")
    private Double temperatureC;

    @Schema(description = "Relative humidity (0-100).", example = "45.0")
    private Double humidity;

    @Schema(description = "Optional raw JSON string for any additional telemetry fields.", example = "{\"pressure\": 101.3}")
    private String rawJson;

    public Instant getTimestamp() {
        return timestamp;
    }

    public Double getTemperatureC() {
        return temperatureC;
    }

    public Double getHumidity() {
        return humidity;
    }

    public String getRawJson() {
        return rawJson;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setTemperatureC(Double temperatureC) {
        this.temperatureC = temperatureC;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public void setRawJson(String rawJson) {
        this.rawJson = rawJson;
    }
}
