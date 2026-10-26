package com.uniti.cil.itta.inventory.change.model;

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
 * Change Request - Change and Work Management domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_change_request",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_change_request_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_change_request_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_change_request_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_change_request_ext", columnList = "external_ref")
        })
public class ChangeRequest extends BaseEntity {

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

    @Column(name = "change_number", nullable = false, length = 255)
    private String changeNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = true, length = 40)
    private ChangeRequestChangeType changeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = true, length = 40)
    private ChangeRequestRiskLevel riskLevel;

    @Column(name = "planned_start", nullable = true)
    private Instant plannedStart;

    @Column(name = "planned_end", nullable = true)
    private Instant plannedEnd;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private ChangeRequestLifecycleStage lifecycleStage;

    public ChangeRequest() {
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

    public String getChangeNumber() { return changeNumber; }
    public void setChangeNumber(String changeNumber) { this.changeNumber = changeNumber; }

    public ChangeRequestChangeType getChangeType() { return changeType; }
    public void setChangeType(ChangeRequestChangeType changeType) { this.changeType = changeType; }

    public ChangeRequestRiskLevel getRiskLevel() { return riskLevel; }
    public void setRiskLevel(ChangeRequestRiskLevel riskLevel) { this.riskLevel = riskLevel; }

    public Instant getPlannedStart() { return plannedStart; }
    public void setPlannedStart(Instant plannedStart) { this.plannedStart = plannedStart; }

    public Instant getPlannedEnd() { return plannedEnd; }
    public void setPlannedEnd(Instant plannedEnd) { this.plannedEnd = plannedEnd; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public ChangeRequestLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(ChangeRequestLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChangeRequest other)) {
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
        return "ChangeRequest{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "changeNumber=" + changeNumber + ", " +
                "changeType=" + changeType + ", " +
                "riskLevel=" + riskLevel + ", " +
                "plannedStart=" + plannedStart + ", " +
                "plannedEnd=" + plannedEnd + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + "}";
    }
}
