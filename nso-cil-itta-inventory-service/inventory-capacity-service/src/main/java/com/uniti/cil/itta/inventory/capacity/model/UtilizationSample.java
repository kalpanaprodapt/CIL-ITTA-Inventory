package com.uniti.cil.itta.inventory.capacity.model;

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
 * Utilization Sample - Capacity Planning domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_utilization_sample",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_utilization_sample_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_utilization_sample_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_utilization_sample_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_utilization_sample_ext", columnList = "external_ref")
        })
public class UtilizationSample extends BaseEntity {

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

    @Column(name = "target_ref", nullable = false, length = 255)
    private String targetRef;

    @Column(name = "metric", nullable = false, length = 255)
    private String metric;

    @Column(name = "sampled_at", nullable = false)
    private Instant sampledAt;

    @Column(name = "value_pct", nullable = false, precision = 18, scale = 4)
    private BigDecimal valuePct;

    @Column(name = "peak", nullable = true)
    private Boolean peak;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private UtilizationSampleLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private UtilizationSampleCriticality criticality;

    public UtilizationSample() {
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

    public String getTargetRef() { return targetRef; }
    public void setTargetRef(String targetRef) { this.targetRef = targetRef; }

    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }

    public Instant getSampledAt() { return sampledAt; }
    public void setSampledAt(Instant sampledAt) { this.sampledAt = sampledAt; }

    public BigDecimal getValuePct() { return valuePct; }
    public void setValuePct(BigDecimal valuePct) { this.valuePct = valuePct; }

    public Boolean getPeak() { return peak; }
    public void setPeak(Boolean peak) { this.peak = peak; }

    public UtilizationSampleLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(UtilizationSampleLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public UtilizationSampleCriticality getCriticality() { return criticality; }
    public void setCriticality(UtilizationSampleCriticality criticality) { this.criticality = criticality; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UtilizationSample other)) {
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
        return "UtilizationSample{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "targetRef=" + targetRef + ", " +
                "metric=" + metric + ", " +
                "sampledAt=" + sampledAt + ", " +
                "valuePct=" + valuePct + ", " +
                "peak=" + peak + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + "}";
    }
}
