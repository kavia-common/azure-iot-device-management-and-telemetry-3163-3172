package com.example.deviceregistrationandtelemetrybackend.device.api;

import java.util.List;

import com.example.deviceregistrationandtelemetrybackend.device.DeviceEntity;
import com.example.deviceregistrationandtelemetrybackend.device.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Device registration and retrieval endpoints.
 */
@RestController
@RequestMapping("/api/devices")
@Tag(name = "Devices", description = "Device registration and retrieval")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register a device",
            description = "Registers a device in Azure IoT Hub when configured; otherwise registers in a local stub. Persists registration locally."
    )
    public DeviceResponse register(@Valid @RequestBody DeviceRegistrationRequest request) {
        DeviceEntity saved = deviceService.register(request);
        return toResponse(saved);
    }

    @GetMapping
    @Operation(summary = "List devices", description = "Lists all locally registered devices.")
    public List<DeviceResponse> list() {
        return deviceService.list().stream().map(DeviceController::toResponse).toList();
    }

    @GetMapping("/{deviceId}")
    @Operation(summary = "Get device", description = "Returns a locally registered device by id.")
    public DeviceResponse get(@PathVariable String deviceId) {
        return toResponse(deviceService.getRequired(deviceId));
    }

    private static DeviceResponse toResponse(DeviceEntity entity) {
        return new DeviceResponse(
                entity.getDeviceId(),
                entity.getDisplayName(),
                entity.isAzureBacked(),
                entity.getCreatedAt()
        );
    }
}
