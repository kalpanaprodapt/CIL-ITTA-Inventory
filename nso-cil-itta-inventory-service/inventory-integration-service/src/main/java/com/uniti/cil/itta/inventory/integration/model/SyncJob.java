package com.uniti.cil.itta.inventory.integration.model;

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
 * Sync Job - Integration Layer domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_sync_job",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_sync_job_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_sync_job_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_sync_job_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_sync_job_ext", columnList = "external_ref")
        })
public class SyncJob extends BaseEntity {

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

    @Column(name = "endpoint_id", nullable = false)
    private UUID endpointId;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = true, length = 40)
    private SyncJobDirection direction;

    @Column(name = "schedule_cron", nullable = true, length = 255)
    private String scheduleCron;

    @Column(name = "last_run_at", nullable = true)
    private Instant lastRunAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "last_status", nullable = true, length = 40)
    private SyncJobLastStatus lastStatus;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private SyncJobCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    public SyncJob() {
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

    public UUID getEndpointId() { return endpointId; }
    public void setEndpointId(UUID endpointId) { this.endpointId = endpointId; }

    public SyncJobDirection getDirection() { return direction; }
    public void setDirection(SyncJobDirection direction) { this.direction = direction; }

    public String getScheduleCron() { return scheduleCron; }
    public void setScheduleCron(String scheduleCron) { this.scheduleCron = scheduleCron; }

    public Instant getLastRunAt() { return lastRunAt; }
    public void setLastRunAt(Instant lastRunAt) { this.lastRunAt = lastRunAt; }

    public SyncJobLastStatus getLastStatus() { return lastStatus; }
    public void setLastStatus(SyncJobLastStatus lastStatus) { this.lastStatus = lastStatus; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public SyncJobCriticality getCriticality() { return criticality; }
    public void setCriticality(SyncJobCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof SyncJob other)) {
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
        return "SyncJob{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "endpointId=" + endpointId + ", " +
                "direction=" + direction + ", " +
                "scheduleCron=" + scheduleCron + ", " +
                "lastRunAt=" + lastRunAt + ", " +
                "lastStatus=" + lastStatus + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + "}";
    }
}
