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
 * Optical Channel - Optical domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_optical_channel",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_optical_channel_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_optical_channel_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_optical_channel_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_optical_channel_ext", columnList = "external_ref")
        })
public class OpticalChannel extends BaseEntity {

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

    @Column(name = "wavelength_nm", nullable = false, precision = 18, scale = 4)
    private BigDecimal wavelengthNm;

    @Column(name = "channel_number", nullable = false)
    private Integer channelNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "grid_type", nullable = true, length = 40)
    private OpticalChannelGridType gridType;

    @Column(name = "bitrate_gbps", nullable = true, precision = 18, scale = 4)
    private BigDecimal bitrateGbps;

    @Column(name = "system_id", nullable = true)
    private UUID systemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private OpticalChannelCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    public OpticalChannel() {
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

    public BigDecimal getWavelengthNm() { return wavelengthNm; }
    public void setWavelengthNm(BigDecimal wavelengthNm) { this.wavelengthNm = wavelengthNm; }

    public Integer getChannelNumber() { return channelNumber; }
    public void setChannelNumber(Integer channelNumber) { this.channelNumber = channelNumber; }

    public OpticalChannelGridType getGridType() { return gridType; }
    public void setGridType(OpticalChannelGridType gridType) { this.gridType = gridType; }

    public BigDecimal getBitrateGbps() { return bitrateGbps; }
    public void setBitrateGbps(BigDecimal bitrateGbps) { this.bitrateGbps = bitrateGbps; }

    public UUID getSystemId() { return systemId; }
    public void setSystemId(UUID systemId) { this.systemId = systemId; }

    public OpticalChannelCriticality getCriticality() { return criticality; }
    public void setCriticality(OpticalChannelCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof OpticalChannel other)) {
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
        return "OpticalChannel{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "wavelengthNm=" + wavelengthNm + ", " +
                "channelNumber=" + channelNumber + ", " +
                "gridType=" + gridType + ", " +
                "bitrateGbps=" + bitrateGbps + ", " +
                "systemId=" + systemId + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + "}";
    }
}
