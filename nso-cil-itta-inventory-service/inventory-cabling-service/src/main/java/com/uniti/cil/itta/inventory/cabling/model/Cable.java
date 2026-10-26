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
 * Cable - Cabling domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_cable",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_cable_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_cable_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_cable_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_cable_ext", columnList = "external_ref")
        })
public class Cable extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "cable_type", nullable = true, length = 40)
    private CableCableType cableType;

    @Column(name = "length_m", nullable = true, precision = 18, scale = 4)
    private BigDecimal lengthM;

    @Column(name = "a_end_port_id", nullable = true)
    private UUID aEndPortId;

    @Column(name = "z_end_port_id", nullable = true)
    private UUID zEndPortId;

    @Column(name = "color", nullable = true, length = 255)
    private String color;

    @Column(name = "label", nullable = true, length = 255)
    private String label;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private CableCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    public Cable() {
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

    public CableCableType getCableType() { return cableType; }
    public void setCableType(CableCableType cableType) { this.cableType = cableType; }

    public BigDecimal getLengthM() { return lengthM; }
    public void setLengthM(BigDecimal lengthM) { this.lengthM = lengthM; }

    public UUID getAEndPortId() { return aEndPortId; }
    public void setAEndPortId(UUID aEndPortId) { this.aEndPortId = aEndPortId; }

    public UUID getZEndPortId() { return zEndPortId; }
    public void setZEndPortId(UUID zEndPortId) { this.zEndPortId = zEndPortId; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public CableCriticality getCriticality() { return criticality; }
    public void setCriticality(CableCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof Cable other)) {
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
        return "Cable{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "cableType=" + cableType + ", " +
                "lengthM=" + lengthM + ", " +
                "aEndPortId=" + aEndPortId + ", " +
                "zEndPortId=" + zEndPortId + ", " +
                "color=" + color + ", " +
                "label=" + label + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + "}";
    }
}
