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
 * Ip Pool - IP Management domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_ip_pool",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_ip_pool_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_ip_pool_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_ip_pool_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_ip_pool_ext", columnList = "external_ref")
        })
public class IpPool extends BaseEntity {

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

    @Column(name = "subnet_id", nullable = false)
    private UUID subnetId;

    @Column(name = "range_start", nullable = false, length = 255)
    private String rangeStart;

    @Column(name = "range_end", nullable = false, length = 255)
    private String rangeEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "pool_type", nullable = true, length = 40)
    private IpPoolPoolType poolType;

    @Column(name = "lease_seconds", nullable = true)
    private Integer leaseSeconds;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private IpPoolLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    public IpPool() {
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

    public UUID getSubnetId() { return subnetId; }
    public void setSubnetId(UUID subnetId) { this.subnetId = subnetId; }

    public String getRangeStart() { return rangeStart; }
    public void setRangeStart(String rangeStart) { this.rangeStart = rangeStart; }

    public String getRangeEnd() { return rangeEnd; }
    public void setRangeEnd(String rangeEnd) { this.rangeEnd = rangeEnd; }

    public IpPoolPoolType getPoolType() { return poolType; }
    public void setPoolType(IpPoolPoolType poolType) { this.poolType = poolType; }

    public Integer getLeaseSeconds() { return leaseSeconds; }
    public void setLeaseSeconds(Integer leaseSeconds) { this.leaseSeconds = leaseSeconds; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public IpPoolLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(IpPoolLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IpPool other)) {
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
        return "IpPool{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "subnetId=" + subnetId + ", " +
                "rangeStart=" + rangeStart + ", " +
                "rangeEnd=" + rangeEnd + ", " +
                "poolType=" + poolType + ", " +
                "leaseSeconds=" + leaseSeconds + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + "}";
    }
}
