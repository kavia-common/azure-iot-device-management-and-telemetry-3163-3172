package com.example.deviceregistrationandtelemetrybackend.iothub;

/**
 * Abstraction for communicating with Azure IoT Hub (or a local stub when not configured).
 * <p>
 * This backend uses this interface so the rest of the application can remain agnostic to whether
 * it is running with real Azure credentials or in local stub mode.
 */
public interface IoTHubClient {

    /**
     * Register (or ensure) a device exists upstream in IoT Hub.
     *
     * @param deviceId stable device identifier (unique)
     * @return the resulting upstream registration (device id + optional connection string)
     */
    DeviceRegistrationResult registerDevice(String deviceId);

    /**
     * Whether this client is backed by Azure IoT Hub (true) or a local stub (false).
     *
     * @return true if Azure-backed; false otherwise
     */
    boolean isAzureBacked();
}
