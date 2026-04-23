package com.example.deviceregistrationandtelemetrybackend.telemetry;

import java.time.Instant;

import com.example.deviceregistrationandtelemetrybackend.device.DeviceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Persisted telemetry event.
 */
@Entity
@Table(
        name = "telemetry",
        indexes = {
                @Index(name = "idx_telemetry_device_time", columnList = "device_id, timestamp")
        }
)
public class TelemetryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEntity device;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "temperature_c")
    private Double temperatureC;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "raw_json", columnDefinition = "CLOB")
    private String rawJson;

    protected TelemetryEntity() {
        // JPA
    }

    public TelemetryEntity(DeviceEntity device, Instant timestamp, Double temperatureC, Double humidity, String rawJson) {
        this.device = device;
        this.timestamp = timestamp;
        this.temperatureC = temperatureC;
        this.humidity = humidity;
        this.rawJson = rawJson;
    }

    public Long getId() {
        return id;
    }

    public DeviceEntity getDevice() {
        return device;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Double getTemperatureC() {
        return temperatureC;
    }

    public Double getHumidity() {
        return humidity;
    }

    public String getRawJson() {
        return rawJson;
    }
}
