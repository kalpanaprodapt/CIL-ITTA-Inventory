package com.uniti.cil.itta.inventory.optical.model;

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
 * Wdm System - Optical domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_wdm_system",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_wdm_system_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_wdm_system_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_wdm_system_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_wdm_system_ext", columnList = "external_ref")
        })
public class WdmSystem extends BaseEntity {

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

    @Column(name = "system_name", nullable = false, length = 255)
    private String systemName;

    @Column(name = "channel_capacity", nullable = false)
    private Integer channelCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "band", nullable = true, length = 40)
    private WdmSystemBand band;

    @Column(name = "span_count", nullable = true)
    private Integer spanCount;

    @Column(name = "vendor_id", nullable = true)
    private UUID vendorId;

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
    private WdmSystemLifecycleStage lifecycleStage;

    public WdmSystem() {
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

    public String getSystemName() { return systemName; }
    public void setSystemName(String systemName) { this.systemName = systemName; }

    public Integer getChannelCapacity() { return channelCapacity; }
    public void setChannelCapacity(Integer channelCapacity) { this.channelCapacity = channelCapacity; }

    public WdmSystemBand getBand() { return band; }
    public void setBand(WdmSystemBand band) { this.band = band; }

    public Integer getSpanCount() { return spanCount; }
    public void setSpanCount(Integer spanCount) { this.spanCount = spanCount; }

    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public WdmSystemLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(WdmSystemLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WdmSystem other)) {
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
        return "WdmSystem{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "systemName=" + systemName + ", " +
                "channelCapacity=" + channelCapacity + ", " +
                "band=" + band + ", " +
                "spanCount=" + spanCount + ", " +
                "vendorId=" + vendorId + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + "}";
    }
}
