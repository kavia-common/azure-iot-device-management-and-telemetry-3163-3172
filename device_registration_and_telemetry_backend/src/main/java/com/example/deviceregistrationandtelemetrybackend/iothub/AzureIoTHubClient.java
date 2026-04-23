package com.example.deviceregistrationandtelemetrybackend.iothub;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Azure IoT Hub implementation that calls the IoT Hub Identity Registry REST API.
 * <p>
 * Note: This is a minimal integration intended for registration workflows. If credentials
 * are not configured, the application should not instantiate this client; it will fall back
 * to {@link LocalStubIoTHubClient}.
 */
public class AzureIoTHubClient implements IoTHubClient {

    private final IoTHubProperties properties;
    private final WebClient webClient;

    public AzureIoTHubClient(IoTHubProperties properties, WebClient webClient) {
        this.properties = properties;
        this.webClient = webClient;
    }

    @Override
    public DeviceRegistrationResult registerDevice(String deviceId) {
        // IoT Hub Identity API:
        // PUT https://{iothub}.azure-devices.net/devices/{deviceId}?api-version=...
        // Authorization: <SAS token>
        // Body: {"deviceId":"...","status":"enabled"}
        String path = "/devices/" + deviceId;

        Map<String, Object> payload = Map.of(
                "deviceId", deviceId,
                "status", "enabled"
        );

        // Best-effort: if the request fails for a transient reason, surface it to caller.
        // In typical usage this service is called via REST endpoints; exceptions will map to 5xx.
        @SuppressWarnings("unchecked")
        Map<String, Object> resp = this.webClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api-version", properties.getApiVersion())
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(10))
                .block();

        // Connection string is not normally returned from the Identity Registry call.
        // We return null here; clients can use their own provisioning mechanism if needed.
        String returnedDeviceId = deviceId;
        if (resp != null && resp.get("deviceId") != null) {
            returnedDeviceId = String.valueOf(resp.get("deviceId"));
        }
        return new DeviceRegistrationResult(returnedDeviceId, null);
    }

    @Override
    public boolean isAzureBacked() {
        return true;
    }

    /**
     * Creates a WebClient preconfigured for IoT Hub REST calls.
     *
     * @param properties IoT Hub properties
     * @return configured WebClient
     */
    public static WebClient buildWebClient(IoTHubProperties properties) {
        String baseUrl = "https://" + properties.getHostname();
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, properties.getSasToken())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
