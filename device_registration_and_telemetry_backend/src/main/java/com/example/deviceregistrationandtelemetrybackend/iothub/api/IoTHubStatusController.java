package com.example.deviceregistrationandtelemetrybackend.iothub.api;

import java.util.Map;

import com.example.deviceregistrationandtelemetrybackend.iothub.IoTHubClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Operational endpoint to indicate whether Azure IoT Hub credentials are configured.
 */
@RestController
@RequestMapping("/api/iothub")
@Tag(name = "IoT Hub", description = "Operational visibility into IoT Hub integration mode")
public class IoTHubStatusController {

    private final IoTHubClient ioTHubClient;

    public IoTHubStatusController(IoTHubClient ioTHubClient) {
        this.ioTHubClient = ioTHubClient;
    }

    @GetMapping("/status")
    @Operation(
            summary = "IoT Hub integration status",
            description = "Returns whether the backend is using Azure IoT Hub or a local stub (when credentials are not configured)."
    )
    public Map<String, Object> status() {
        return Map.of(
                "azureBacked", ioTHubClient.isAzureBacked()
        );
    }
}
