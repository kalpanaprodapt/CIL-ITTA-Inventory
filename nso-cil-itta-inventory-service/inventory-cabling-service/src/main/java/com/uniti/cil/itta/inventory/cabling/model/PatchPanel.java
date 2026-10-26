package com.uniti.cil.itta.inventory.cabling.model;

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
 * Patch Panel - Cabling domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_patch_panel",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_patch_panel_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_patch_panel_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_patch_panel_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_patch_panel_ext", columnList = "external_ref")
        })
public class PatchPanel extends BaseEntity {

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

    @Column(name = "room_id", nullable = false)
    private UUID roomId;

    @Column(name = "rack_id", nullable = true)
    private UUID rackId;

    @Enumerated(EnumType.STRING)
    @Column(name = "panel_type", nullable = true, length = 40)
    private PatchPanelPanelType panelType;

    @Column(name = "port_count", nullable = false)
    private Integer portCount;

    @Column(name = "label", nullable = true, length = 255)
    private String label;

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
    private PatchPanelLifecycleStage lifecycleStage;

    public PatchPanel() {
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

    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }

    public UUID getRackId() { return rackId; }
    public void setRackId(UUID rackId) { this.rackId = rackId; }

    public PatchPanelPanelType getPanelType() { return panelType; }
    public void setPanelType(PatchPanelPanelType panelType) { this.panelType = panelType; }

    public Integer getPortCount() { return portCount; }
    public void setPortCount(Integer portCount) { this.portCount = portCount; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public PatchPanelLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(PatchPanelLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatchPanel other)) {
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
        return "PatchPanel{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "roomId=" + roomId + ", " +
                "rackId=" + rackId + ", " +
                "panelType=" + panelType + ", " +
                "portCount=" + portCount + ", " +
                "label=" + label + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + "}";
    }
}
