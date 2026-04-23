package com.example.deviceregistrationandtelemetrybackend.device;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Persisted device registration record.
 */
@Entity
@Table(name = "devices")
public class DeviceEntity {

    @Id
    @Column(name = "device_id", nullable = false, updatable = false, length = 128)
    private String deviceId;

    @Column(name = "display_name", length = 256)
    private String displayName;

    @Column(name = "azure_backed", nullable = false)
    private boolean azureBacked;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected DeviceEntity() {
        // JPA
    }

    public DeviceEntity(String deviceId, String displayName, boolean azureBacked, Instant createdAt) {
        this.deviceId = deviceId;
        this.displayName = displayName;
        this.azureBacked = azureBacked;
        this.createdAt = createdAt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isAzureBacked() {
        return azureBacked;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAzureBacked(boolean azureBacked) {
        this.azureBacked = azureBacked;
    }
}
