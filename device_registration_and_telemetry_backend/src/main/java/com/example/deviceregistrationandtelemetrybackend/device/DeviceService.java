package com.example.deviceregistrationandtelemetrybackend.device;

import java.time.Instant;
import java.util.List;

import com.example.deviceregistrationandtelemetrybackend.device.api.DeviceRegistrationRequest;
import com.example.deviceregistrationandtelemetrybackend.iothub.IoTHubClient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business logic for device registration and retrieval.
 */
@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final IoTHubClient ioTHubClient;

    public DeviceService(DeviceRepository deviceRepository, IoTHubClient ioTHubClient) {
        this.deviceRepository = deviceRepository;
        this.ioTHubClient = ioTHubClient;
    }

    /**
     * Registers a device in IoT Hub (or stub) and persists it locally.
     *
     * @param request device registration request
     * @return persisted device entity
     */
    @Transactional
    public DeviceEntity register(DeviceRegistrationRequest request) {
        String deviceId = request.getDeviceId().trim();

        // Ensure exists upstream (Azure or stub)
        ioTHubClient.registerDevice(deviceId);

        // Upsert locally
        DeviceEntity entity = deviceRepository.findById(deviceId)
                .orElseGet(() -> new DeviceEntity(deviceId, request.getDisplayName(), ioTHubClient.isAzureBacked(), Instant.now()));

        if (request.getDisplayName() != null && !request.getDisplayName().isBlank()) {
            entity.setDisplayName(request.getDisplayName().trim());
        }
        entity.setAzureBacked(ioTHubClient.isAzureBacked());

        return deviceRepository.save(entity);
    }

    /**
     * Fetch a device or throw.
     *
     * @param deviceId device id
     * @return device
     */
    @Transactional(readOnly = true)
    public DeviceEntity getRequired(String deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device not found: " + deviceId));
    }

    /**
     * List all devices.
     *
     * @return devices
     */
    @Transactional(readOnly = true)
    public List<DeviceEntity> list() {
        return deviceRepository.findAll();
    }
}
