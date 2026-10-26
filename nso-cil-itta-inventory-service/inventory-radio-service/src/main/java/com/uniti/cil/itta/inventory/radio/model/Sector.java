package com.uniti.cil.itta.inventory.radio.model;

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
 * Sector - Radio and Mobile domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_sector",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_sector_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_sector_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_sector_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_sector_ext", columnList = "external_ref")
        })
public class Sector extends BaseEntity {

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

    @Column(name = "base_station_id", nullable = false)
    private UUID baseStationId;

    @Column(name = "sector_number", nullable = false)
    private Integer sectorNumber;

    @Column(name = "pci", nullable = true)
    private Integer pci;

    @Column(name = "frequency_mhz", nullable = true)
    private Integer frequencyMhz;

    @Column(name = "bandwidth_mhz", nullable = true)
    private Integer bandwidthMhz;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private SectorCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    public Sector() {
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

    public UUID getBaseStationId() { return baseStationId; }
    public void setBaseStationId(UUID baseStationId) { this.baseStationId = baseStationId; }

    public Integer getSectorNumber() { return sectorNumber; }
    public void setSectorNumber(Integer sectorNumber) { this.sectorNumber = sectorNumber; }

    public Integer getPci() { return pci; }
    public void setPci(Integer pci) { this.pci = pci; }

    public Integer getFrequencyMhz() { return frequencyMhz; }
    public void setFrequencyMhz(Integer frequencyMhz) { this.frequencyMhz = frequencyMhz; }

    public Integer getBandwidthMhz() { return bandwidthMhz; }
    public void setBandwidthMhz(Integer bandwidthMhz) { this.bandwidthMhz = bandwidthMhz; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public SectorCriticality getCriticality() { return criticality; }
    public void setCriticality(SectorCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof Sector other)) {
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
        return "Sector{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "baseStationId=" + baseStationId + ", " +
                "sectorNumber=" + sectorNumber + ", " +
                "pci=" + pci + ", " +
                "frequencyMhz=" + frequencyMhz + ", " +
                "bandwidthMhz=" + bandwidthMhz + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + "}";
    }
}
