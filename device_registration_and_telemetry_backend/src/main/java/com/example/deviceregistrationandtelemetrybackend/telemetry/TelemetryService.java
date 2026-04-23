package com.example.deviceregistrationandtelemetrybackend.telemetry;

import java.time.Instant;
import java.util.List;

import com.example.deviceregistrationandtelemetrybackend.device.DeviceEntity;
import com.example.deviceregistrationandtelemetrybackend.device.DeviceService;
import com.example.deviceregistrationandtelemetrybackend.telemetry.api.TelemetryIngestRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business logic for telemetry ingestion and retrieval.
 */
@Service
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;
    private final DeviceService deviceService;

    public TelemetryService(TelemetryRepository telemetryRepository, DeviceService deviceService) {
        this.telemetryRepository = telemetryRepository;
        this.deviceService = deviceService;
    }

    /**
     * Ingest a telemetry event for a device (device must be registered).
     *
     * @param deviceId device id
     * @param req      telemetry request
     * @return persisted entity
     */
    @Transactional
    public TelemetryEntity ingest(String deviceId, TelemetryIngestRequest req) {
        DeviceEntity device = deviceService.getRequired(deviceId);

        TelemetryEntity entity = new TelemetryEntity(
                device,
                req.getTimestamp(),
                req.getTemperatureC(),
                req.getHumidity(),
                req.getRawJson()
        );

        return telemetryRepository.save(entity);
    }

    /**
     * Query recent telemetry for a device with optional time range.
     *
     * @param deviceId device id
     * @param from     optional start time (inclusive)
     * @param to       optional end time (inclusive)
     * @param limit    max number of records
     * @return list of telemetry events
     */
    @Transactional(readOnly = true)
    public List<TelemetryEntity> query(String deviceId, Instant from, Instant to, int limit) {
        // Ensure device exists
        deviceService.getRequired(deviceId);

        int safeLimit = Math.max(1, Math.min(limit, 1000));
        PageRequest page = PageRequest.of(0, safeLimit);

        if (from != null && to != null) {
            return telemetryRepository.findByDevice_DeviceIdAndTimestampBetweenOrderByTimestampDesc(deviceId, from, to, page);
        }
        return telemetryRepository.findByDevice_DeviceIdOrderByTimestampDesc(deviceId, page);
    }
}
