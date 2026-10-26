package com.uniti.cil.itta.inventory.facility.model;

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
 * Rack Unit - Facility domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_rack_unit",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_rack_unit_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_rack_unit_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_rack_unit_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_rack_unit_ext", columnList = "external_ref")
        })
public class RackUnit extends BaseEntity {

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

    @Column(name = "rack_id", nullable = false)
    private UUID rackId;

    @Column(name = "unit_number", nullable = false)
    private Integer unitNumber;

    @Column(name = "occupied", nullable = true)
    private Boolean occupied;

    @Column(name = "mounted_device_id", nullable = true)
    private UUID mountedDeviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "face", nullable = true, length = 40)
    private RackUnitFace face;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private RackUnitCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    public RackUnit() {
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

    public UUID getRackId() { return rackId; }
    public void setRackId(UUID rackId) { this.rackId = rackId; }

    public Integer getUnitNumber() { return unitNumber; }
    public void setUnitNumber(Integer unitNumber) { this.unitNumber = unitNumber; }

    public Boolean getOccupied() { return occupied; }
    public void setOccupied(Boolean occupied) { this.occupied = occupied; }

    public UUID getMountedDeviceId() { return mountedDeviceId; }
    public void setMountedDeviceId(UUID mountedDeviceId) { this.mountedDeviceId = mountedDeviceId; }

    public RackUnitFace getFace() { return face; }
    public void setFace(RackUnitFace face) { this.face = face; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public RackUnitCriticality getCriticality() { return criticality; }
    public void setCriticality(RackUnitCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RackUnit other)) {
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
        return "RackUnit{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "rackId=" + rackId + ", " +
                "unitNumber=" + unitNumber + ", " +
                "occupied=" + occupied + ", " +
                "mountedDeviceId=" + mountedDeviceId + ", " +
                "face=" + face + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + "}";
    }
}
