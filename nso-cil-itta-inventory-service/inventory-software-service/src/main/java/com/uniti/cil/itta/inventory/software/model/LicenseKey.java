package com.uniti.cil.itta.inventory.software.model;

import com.uniti.cil.itta.inventory.common.model.BaseEntity;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/**
 * License Key - Software and Licensing domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_license_key",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_license_key_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_license_key_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_license_key_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_license_key_ext", columnList = "external_ref")
        })
public class LicenseKey extends BaseEntity {

    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = true, length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LifecycleStatus status;

    @Column(name = "external_ref", nullable = true, length = 255)
    private String externalRef;

    @Column(name = "pool_id", nullable = false)
    private UUID poolId;

    @Column(name = "key_value", nullable = false, length = 255)
    private String keyValue;

    @Column(name = "feature", nullable = false, length = 255)
    private String feature;

    @Column(name = "expires_on", nullable = true)
    private LocalDate expiresOn;

    @Column(name = "assigned_device_id", nullable = true)
    private UUID assignedDeviceId;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private LicenseKeyLifecycleStage lifecycleStage;

    public LicenseKey() {
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LifecycleStatus getStatus() { return status; }
    public void setStatus(LifecycleStatus status) { this.status = status; }

    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }

    public UUID getPoolId() { return poolId; }
    public void setPoolId(UUID poolId) { this.poolId = poolId; }

    public String getKeyValue() { return keyValue; }
    public void setKeyValue(String keyValue) { this.keyValue = keyValue; }

    public String getFeature() { return feature; }
    public void setFeature(String feature) { this.feature = feature; }

    public LocalDate getExpiresOn() { return expiresOn; }
    public void setExpiresOn(LocalDate expiresOn) { this.expiresOn = expiresOn; }

    public UUID getAssignedDeviceId() { return assignedDeviceId; }
    public void setAssignedDeviceId(UUID assignedDeviceId) { this.assignedDeviceId = assignedDeviceId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public LicenseKeyLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(LicenseKeyLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenseKey other)) {
            return false;
        }
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "LicenseKey{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "poolId=" + poolId + ", " +
                "keyValue=" + keyValue + ", " +
                "feature=" + feature + ", " +
                "expiresOn=" + expiresOn + ", " +
                "assignedDeviceId=" + assignedDeviceId + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + "}";
    }
}
