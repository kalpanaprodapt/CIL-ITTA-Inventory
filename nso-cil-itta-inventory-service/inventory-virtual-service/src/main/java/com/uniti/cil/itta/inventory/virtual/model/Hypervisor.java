package com.uniti.cil.itta.inventory.virtual.model;

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
 * Hypervisor - Virtual and Cloud domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_hypervisor",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_hypervisor_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_hypervisor_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_hypervisor_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_hypervisor_ext", columnList = "external_ref")
        })
public class Hypervisor extends BaseEntity {

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

    @Column(name = "host_device_id", nullable = false)
    private UUID hostDeviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "hypervisor_type", nullable = true, length = 40)
    private HypervisorHypervisorType hypervisorType;

    @Column(name = "hv_version", nullable = true, length = 255)
    private String hvVersion;

    @Column(name = "vcpu_capacity", nullable = true)
    private Integer vcpuCapacity;

    @Column(name = "memory_gb_capacity", nullable = true)
    private Integer memoryGbCapacity;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private HypervisorCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    public Hypervisor() {
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

    public UUID getHostDeviceId() { return hostDeviceId; }
    public void setHostDeviceId(UUID hostDeviceId) { this.hostDeviceId = hostDeviceId; }

    public HypervisorHypervisorType getHypervisorType() { return hypervisorType; }
    public void setHypervisorType(HypervisorHypervisorType hypervisorType) { this.hypervisorType = hypervisorType; }

    public String getHvVersion() { return hvVersion; }
    public void setHvVersion(String hvVersion) { this.hvVersion = hvVersion; }

    public Integer getVcpuCapacity() { return vcpuCapacity; }
    public void setVcpuCapacity(Integer vcpuCapacity) { this.vcpuCapacity = vcpuCapacity; }

    public Integer getMemoryGbCapacity() { return memoryGbCapacity; }
    public void setMemoryGbCapacity(Integer memoryGbCapacity) { this.memoryGbCapacity = memoryGbCapacity; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public HypervisorCriticality getCriticality() { return criticality; }
    public void setCriticality(HypervisorCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hypervisor other)) {
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
        return "Hypervisor{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "hostDeviceId=" + hostDeviceId + ", " +
                "hypervisorType=" + hypervisorType + ", " +
                "hvVersion=" + hvVersion + ", " +
                "vcpuCapacity=" + vcpuCapacity + ", " +
                "memoryGbCapacity=" + memoryGbCapacity + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + "}";
    }
}
