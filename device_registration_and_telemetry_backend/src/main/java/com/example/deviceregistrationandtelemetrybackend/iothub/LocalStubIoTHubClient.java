package com.example.deviceregistrationandtelemetrybackend.iothub;

/**
 * Local stub implementation of IoT Hub integration.
 * <p>
 * Used automatically when Azure credentials are not configured.
 */
public class LocalStubIoTHubClient implements IoTHubClient {

    @Override
    public DeviceRegistrationResult registerDevice(String deviceId) {
        // Stub: we "register" successfully and do not return a connection string.
        return new DeviceRegistrationResult(deviceId, null);
    }

    @Override
    public boolean isAzureBacked() {
        return false;
    }
}
