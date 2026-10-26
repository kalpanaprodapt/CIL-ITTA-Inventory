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
 * Threshold Policy - Capacity Planning domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_threshold_policy",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_threshold_policy_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_threshold_policy_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_threshold_policy_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_threshold_policy_ext", columnList = "external_ref")
        })
public class ThresholdPolicy extends BaseEntity {

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

    @Column(name = "policy_name", nullable = false, length = 255)
    private String policyName;

    @Column(name = "metric", nullable = false, length = 255)
    private String metric;

    @Column(name = "warn_pct", nullable = false, precision = 18, scale = 4)
    private BigDecimal warnPct;

    @Column(name = "crit_pct", nullable = false, precision = 18, scale = 4)
    private BigDecimal critPct;

    @Column(name = "evaluation_window_minutes", nullable = true)
    private Integer evaluationWindowMinutes;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private ThresholdPolicyCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    public ThresholdPolicy() {
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

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }

    public BigDecimal getWarnPct() { return warnPct; }
    public void setWarnPct(BigDecimal warnPct) { this.warnPct = warnPct; }

    public BigDecimal getCritPct() { return critPct; }
    public void setCritPct(BigDecimal critPct) { this.critPct = critPct; }

    public Integer getEvaluationWindowMinutes() { return evaluationWindowMinutes; }
    public void setEvaluationWindowMinutes(Integer evaluationWindowMinutes) { this.evaluationWindowMinutes = evaluationWindowMinutes; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public ThresholdPolicyCriticality getCriticality() { return criticality; }
    public void setCriticality(ThresholdPolicyCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof ThresholdPolicy other)) {
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
        return "ThresholdPolicy{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "policyName=" + policyName + ", " +
                "metric=" + metric + ", " +
                "warnPct=" + warnPct + ", " +
                "critPct=" + critPct + ", " +
                "evaluationWindowMinutes=" + evaluationWindowMinutes + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + "}";
    }
}
