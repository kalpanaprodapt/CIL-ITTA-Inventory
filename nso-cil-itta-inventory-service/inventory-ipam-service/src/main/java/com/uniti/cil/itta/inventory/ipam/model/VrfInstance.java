package com.uniti.cil.itta.inventory.ipam.model;

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
 * Vrf Instance - IP Management domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_vrf_instance",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_vrf_instance_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_vrf_instance_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_vrf_instance_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_vrf_instance_ext", columnList = "external_ref")
        })
public class VrfInstance extends BaseEntity {

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

    @Column(name = "vrf_name", nullable = false, length = 255)
    private String vrfName;

    @Column(name = "route_distinguisher", nullable = false, length = 255)
    private String routeDistinguisher;

    @Column(name = "import_targets", nullable = true, length = 255)
    private String importTargets;

    @Column(name = "export_targets", nullable = true, length = 255)
    private String exportTargets;

    @Column(name = "device_id", nullable = true)
    private UUID deviceId;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private VrfInstanceLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public VrfInstance() {
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

    public String getVrfName() { return vrfName; }
    public void setVrfName(String vrfName) { this.vrfName = vrfName; }

    public String getRouteDistinguisher() { return routeDistinguisher; }
    public void setRouteDistinguisher(String routeDistinguisher) { this.routeDistinguisher = routeDistinguisher; }

    public String getImportTargets() { return importTargets; }
    public void setImportTargets(String importTargets) { this.importTargets = importTargets; }

    public String getExportTargets() { return exportTargets; }
    public void setExportTargets(String exportTargets) { this.exportTargets = exportTargets; }

    public UUID getDeviceId() { return deviceId; }
    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public VrfInstanceLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(VrfInstanceLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VrfInstance other)) {
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
        return "VrfInstance{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "vrfName=" + vrfName + ", " +
                "routeDistinguisher=" + routeDistinguisher + ", " +
                "importTargets=" + importTargets + ", " +
                "exportTargets=" + exportTargets + ", " +
                "deviceId=" + deviceId + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + "}";
    }
}
