package com.uniti.cil.itta.inventory.customer.model;

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
 * Sla Profile - Customers and SLA domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_sla_profile",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_sla_profile_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_sla_profile_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_sla_profile_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_sla_profile_ext", columnList = "external_ref")
        })
public class SlaProfile extends BaseEntity {

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

    @Column(name = "profile_name", nullable = false, length = 255)
    private String profileName;

    @Column(name = "availability_pct", nullable = false, precision = 18, scale = 4)
    private BigDecimal availabilityPct;

    @Column(name = "mttr_minutes", nullable = true)
    private Integer mttrMinutes;

    @Column(name = "latency_ms", nullable = true, precision = 18, scale = 4)
    private BigDecimal latencyMs;

    @Column(name = "jitter_ms", nullable = true, precision = 18, scale = 4)
    private BigDecimal jitterMs;

    @Column(name = "credit_policy", nullable = true, length = 4000)
    private String creditPolicy;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private SlaProfileCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    public SlaProfile() {
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

    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }

    public BigDecimal getAvailabilityPct() { return availabilityPct; }
    public void setAvailabilityPct(BigDecimal availabilityPct) { this.availabilityPct = availabilityPct; }

    public Integer getMttrMinutes() { return mttrMinutes; }
    public void setMttrMinutes(Integer mttrMinutes) { this.mttrMinutes = mttrMinutes; }

    public BigDecimal getLatencyMs() { return latencyMs; }
    public void setLatencyMs(BigDecimal latencyMs) { this.latencyMs = latencyMs; }

    public BigDecimal getJitterMs() { return jitterMs; }
    public void setJitterMs(BigDecimal jitterMs) { this.jitterMs = jitterMs; }

    public String getCreditPolicy() { return creditPolicy; }
    public void setCreditPolicy(String creditPolicy) { this.creditPolicy = creditPolicy; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public SlaProfileCriticality getCriticality() { return criticality; }
    public void setCriticality(SlaProfileCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SlaProfile other)) {
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
        return "SlaProfile{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "profileName=" + profileName + ", " +
                "availabilityPct=" + availabilityPct + ", " +
                "mttrMinutes=" + mttrMinutes + ", " +
                "latencyMs=" + latencyMs + ", " +
                "jitterMs=" + jitterMs + ", " +
                "creditPolicy=" + creditPolicy + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + "}";
    }
}
