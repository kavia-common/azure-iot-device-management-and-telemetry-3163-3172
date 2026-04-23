package com.example.deviceregistrationandtelemetrybackend.telemetry;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for telemetry events.
 */
@Repository
public interface TelemetryRepository extends JpaRepository<TelemetryEntity, Long> {

    List<TelemetryEntity> findByDevice_DeviceIdOrderByTimestampDesc(String deviceId, Pageable pageable);

    List<TelemetryEntity> findByDevice_DeviceIdAndTimestampBetweenOrderByTimestampDesc(
            String deviceId,
            Instant from,
            Instant to,
            Pageable pageable
    );
}
