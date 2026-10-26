package com.uniti.cil.itta.inventory.device.model;

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
 * Chassis - Network Device domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_chassis",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_chassis_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_chassis_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_chassis_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_chassis_ext", columnList = "external_ref")
        })
public class Chassis extends BaseEntity {

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

    @Column(name = "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "slot_count", nullable = false)
    private Integer slotCount;

    @Column(name = "serial_number", nullable = true, length = 255)
    private String serialNumber;

    @Column(name = "backplane_gbps", nullable = true, precision = 18, scale = 4)
    private BigDecimal backplaneGbps;

    @Column(name = "redundancy_mode", nullable = true, length = 255)
    private String redundancyMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private ChassisLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private ChassisCriticality criticality;

    public Chassis() {
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

    public UUID getDeviceId() { return deviceId; }
    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }

    public Integer getSlotCount() { return slotCount; }
    public void setSlotCount(Integer slotCount) { this.slotCount = slotCount; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public BigDecimal getBackplaneGbps() { return backplaneGbps; }
    public void setBackplaneGbps(BigDecimal backplaneGbps) { this.backplaneGbps = backplaneGbps; }

    public String getRedundancyMode() { return redundancyMode; }
    public void setRedundancyMode(String redundancyMode) { this.redundancyMode = redundancyMode; }

    public ChassisLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(ChassisLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public ChassisCriticality getCriticality() { return criticality; }
    public void setCriticality(ChassisCriticality criticality) { this.criticality = criticality; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chassis other)) {
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
        return "Chassis{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "deviceId=" + deviceId + ", " +
                "slotCount=" + slotCount + ", " +
                "serialNumber=" + serialNumber + ", " +
                "backplaneGbps=" + backplaneGbps + ", " +
                "redundancyMode=" + redundancyMode + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + "}";
    }
}
