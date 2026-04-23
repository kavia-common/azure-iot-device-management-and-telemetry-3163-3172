package com.example.deviceregistrationandtelemetrybackend.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for persisted devices.
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {
}
