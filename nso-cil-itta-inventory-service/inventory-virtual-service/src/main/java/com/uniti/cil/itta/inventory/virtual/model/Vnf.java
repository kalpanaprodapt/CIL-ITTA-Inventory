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
 * Vnf - Virtual and Cloud domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_vnf",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_vnf_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_vnf_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_vnf_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_vnf_ext", columnList = "external_ref")
        })
public class Vnf extends BaseEntity {

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

    @Column(name = "vnf_name", nullable = false, length = 255)
    private String vnfName;

    @Column(name = "vnf_type", nullable = false, length = 255)
    private String vnfType;

    @Column(name = "flavor", nullable = true, length = 255)
    private String flavor;

    @Column(name = "vcpu", nullable = true)
    private Integer vcpu;

    @Column(name = "memory_gb", nullable = true)
    private Integer memoryGb;

    @Column(name = "instance_count", nullable = true)
    private Integer instanceCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private VnfLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private VnfCriticality criticality;

    public Vnf() {
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

    public String getVnfName() { return vnfName; }
    public void setVnfName(String vnfName) { this.vnfName = vnfName; }

    public String getVnfType() { return vnfType; }
    public void setVnfType(String vnfType) { this.vnfType = vnfType; }

    public String getFlavor() { return flavor; }
    public void setFlavor(String flavor) { this.flavor = flavor; }

    public Integer getVcpu() { return vcpu; }
    public void setVcpu(Integer vcpu) { this.vcpu = vcpu; }

    public Integer getMemoryGb() { return memoryGb; }
    public void setMemoryGb(Integer memoryGb) { this.memoryGb = memoryGb; }

    public Integer getInstanceCount() { return instanceCount; }
    public void setInstanceCount(Integer instanceCount) { this.instanceCount = instanceCount; }

    public VnfLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(VnfLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public VnfCriticality getCriticality() { return criticality; }
    public void setCriticality(VnfCriticality criticality) { this.criticality = criticality; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vnf other)) {
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
        return "Vnf{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "vnfName=" + vnfName + ", " +
                "vnfType=" + vnfType + ", " +
                "flavor=" + flavor + ", " +
                "vcpu=" + vcpu + ", " +
                "memoryGb=" + memoryGb + ", " +
                "instanceCount=" + instanceCount + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + "}";
    }
}
