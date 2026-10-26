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
 * Network Device - Network Device domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_network_device",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_network_device_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_network_device_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_network_device_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_network_device_ext", columnList = "external_ref")
        })
public class NetworkDevice extends BaseEntity {

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

    @Column(name = "hostname", nullable = false, length = 255)
    private String hostname;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_role", nullable = true, length = 40)
    private NetworkDeviceDeviceRole deviceRole;

    @Column(name = "mgmt_ip", nullable = true, length = 255)
    private String mgmtIp;

    @Column(name = "serial_number", nullable = true, length = 255)
    private String serialNumber;

    @Column(name = "model_id", nullable = true)
    private UUID modelId;

    @Column(name = "rack_unit_id", nullable = true)
    private UUID rackUnitId;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private NetworkDeviceLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    public NetworkDevice() {
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

    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }

    public NetworkDeviceDeviceRole getDeviceRole() { return deviceRole; }
    public void setDeviceRole(NetworkDeviceDeviceRole deviceRole) { this.deviceRole = deviceRole; }

    public String getMgmtIp() { return mgmtIp; }
    public void setMgmtIp(String mgmtIp) { this.mgmtIp = mgmtIp; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public UUID getModelId() { return modelId; }
    public void setModelId(UUID modelId) { this.modelId = modelId; }

    public UUID getRackUnitId() { return rackUnitId; }
    public void setRackUnitId(UUID rackUnitId) { this.rackUnitId = rackUnitId; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public NetworkDeviceLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(NetworkDeviceLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetworkDevice other)) {
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
        return "NetworkDevice{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "hostname=" + hostname + ", " +
                "deviceRole=" + deviceRole + ", " +
                "mgmtIp=" + mgmtIp + ", " +
                "serialNumber=" + serialNumber + ", " +
                "modelId=" + modelId + ", " +
                "rackUnitId=" + rackUnitId + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + "}";
    }
}
