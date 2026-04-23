package com.example.deviceregistrationandtelemetrybackend.iothub;

/**
 * Result of registering a device in IoT Hub (or stub).
 *
 * @param deviceId          registered device id
 * @param connectionString  optional device connection string (may be null when not available)
 */
public record DeviceRegistrationResult(String deviceId, String connectionString) { }
