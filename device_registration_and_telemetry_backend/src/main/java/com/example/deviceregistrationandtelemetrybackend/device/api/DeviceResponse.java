package com.example.deviceregistrationandtelemetrybackend.device.api;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Device response DTO.
 *
 * @param deviceId     unique device id
 * @param displayName  optional display name
 * @param azureBacked  whether the device was registered with Azure IoT Hub (true) or stub mode (false)
 * @param createdAt    creation timestamp
 */
@Schema(description = "Registered device information.")
public record DeviceResponse(
        @Schema(example = "device-001") String deviceId,
        @Schema(example = "Boiler Room Sensor A") String displayName,
        @Schema(example = "false") boolean azureBacked,
        Instant createdAt
) { }
