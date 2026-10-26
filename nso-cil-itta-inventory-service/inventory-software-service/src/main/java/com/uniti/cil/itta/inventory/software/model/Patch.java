package com.uniti.cil.itta.inventory.software.model;

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
 * Patch - Software and Licensing domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_patch",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_patch_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_patch_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_patch_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_patch_ext", columnList = "external_ref")
        })
public class Patch extends BaseEntity {

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

    @Column(name = "version_id", nullable = false)
    private UUID versionId;

    @Column(name = "patch_id", nullable = false, length = 255)
    private String patchId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = true, length = 40)
    private PatchSeverity severity;

    @Column(name = "released_on", nullable = true)
    private LocalDate releasedOn;

    @Column(name = "reboot_required", nullable = true)
    private Boolean rebootRequired;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private PatchCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    public Patch() {
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

    public UUID getVersionId() { return versionId; }
    public void setVersionId(UUID versionId) { this.versionId = versionId; }

    public String getPatchId() { return patchId; }
    public void setPatchId(String patchId) { this.patchId = patchId; }

    public PatchSeverity getSeverity() { return severity; }
    public void setSeverity(PatchSeverity severity) { this.severity = severity; }

    public LocalDate getReleasedOn() { return releasedOn; }
    public void setReleasedOn(LocalDate releasedOn) { this.releasedOn = releasedOn; }

    public Boolean getRebootRequired() { return rebootRequired; }
    public void setRebootRequired(Boolean rebootRequired) { this.rebootRequired = rebootRequired; }

    public PatchCriticality getCriticality() { return criticality; }
    public void setCriticality(PatchCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patch other)) {
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
        return "Patch{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "versionId=" + versionId + ", " +
                "patchId=" + patchId + ", " +
                "severity=" + severity + ", " +
                "releasedOn=" + releasedOn + ", " +
                "rebootRequired=" + rebootRequired + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + "}";
    }
}
