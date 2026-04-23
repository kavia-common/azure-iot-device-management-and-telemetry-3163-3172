package com.example.deviceregistrationandtelemetrybackend.device.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Device registration request.
 */
@Schema(description = "Request to register a device (and optionally set a display name).")
public class DeviceRegistrationRequest {

    @NotBlank
    @Size(max = 128)
    @Schema(description = "Unique device identifier.", example = "device-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String deviceId;

    @Size(max = 256)
    @Schema(description = "Optional display name.", example = "Boiler Room Sensor A")
    private String displayName;

    public String getDeviceId() {
        return deviceId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
