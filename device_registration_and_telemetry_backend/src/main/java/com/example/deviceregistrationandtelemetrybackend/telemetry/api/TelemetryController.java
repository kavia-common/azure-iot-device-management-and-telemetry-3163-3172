package com.example.deviceregistrationandtelemetrybackend.telemetry.api;

import java.time.Instant;
import java.util.List;

import com.example.deviceregistrationandtelemetrybackend.telemetry.TelemetryEntity;
import com.example.deviceregistrationandtelemetrybackend.telemetry.TelemetryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Telemetry ingestion and retrieval endpoints.
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/telemetry")
@Tag(name = "Telemetry", description = "Telemetry ingestion and retrieval")
@Validated
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Ingest telemetry",
            description = "Ingests a telemetry event for a registered device and persists it."
    )
    public TelemetryResponse ingest(
            @PathVariable String deviceId,
            @Valid @RequestBody TelemetryIngestRequest request
    ) {
        TelemetryEntity saved = telemetryService.ingest(deviceId, request);
        return toResponse(saved);
    }

    @GetMapping
    @Operation(
            summary = "Get telemetry",
            description = "Retrieves recent telemetry events for a device. Supports optional time window and limit."
    )
    public List<TelemetryResponse> getTelemetry(
            @PathVariable String deviceId,
            @Parameter(description = "Start timestamp (inclusive) in ISO-8601", example = "2026-01-01T00:00:00Z")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant from,
            @Parameter(description = "End timestamp (inclusive) in ISO-8601", example = "2026-01-02T00:00:00Z")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant to,
            @RequestParam(defaultValue = "100")
            @Min(1) @Max(1000)
            int limit
    ) {
        return telemetryService.query(deviceId, from, to, limit).stream()
                .map(TelemetryController::toResponse)
                .toList();
    }

    private static TelemetryResponse toResponse(TelemetryEntity e) {
        return new TelemetryResponse(
                e.getId(),
                e.getDevice().getDeviceId(),
                e.getTimestamp(),
                e.getTemperatureC(),
                e.getHumidity(),
                e.getRawJson()
        );
    }
}
