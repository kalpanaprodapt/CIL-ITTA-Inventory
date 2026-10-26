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
 * Fiber Strand - Cabling domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_fiber_strand",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_fiber_strand_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_fiber_strand_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_fiber_strand_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_fiber_strand_ext", columnList = "external_ref")
        })
public class FiberStrand extends BaseEntity {

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

    @Column(name = "cable_id", nullable = false)
    private UUID cableId;

    @Column(name = "strand_number", nullable = false)
    private Integer strandNumber;

    @Column(name = "buffer_tube", nullable = true, length = 255)
    private String bufferTube;

    @Column(name = "tube_color", nullable = true, length = 255)
    private String tubeColor;

    @Column(name = "attenuation_db", nullable = true, precision = 18, scale = 4)
    private BigDecimal attenuationDb;

    @Column(name = "in_use", nullable = true)
    private Boolean inUse;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private FiberStrandCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    public FiberStrand() {
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

    public UUID getCableId() { return cableId; }
    public void setCableId(UUID cableId) { this.cableId = cableId; }

    public Integer getStrandNumber() { return strandNumber; }
    public void setStrandNumber(Integer strandNumber) { this.strandNumber = strandNumber; }

    public String getBufferTube() { return bufferTube; }
    public void setBufferTube(String bufferTube) { this.bufferTube = bufferTube; }

    public String getTubeColor() { return tubeColor; }
    public void setTubeColor(String tubeColor) { this.tubeColor = tubeColor; }

    public BigDecimal getAttenuationDb() { return attenuationDb; }
    public void setAttenuationDb(BigDecimal attenuationDb) { this.attenuationDb = attenuationDb; }

    public Boolean getInUse() { return inUse; }
    public void setInUse(Boolean inUse) { this.inUse = inUse; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public FiberStrandCriticality getCriticality() { return criticality; }
    public void setCriticality(FiberStrandCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof FiberStrand other)) {
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
        return "FiberStrand{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "cableId=" + cableId + ", " +
                "strandNumber=" + strandNumber + ", " +
                "bufferTube=" + bufferTube + ", " +
                "tubeColor=" + tubeColor + ", " +
                "attenuationDb=" + attenuationDb + ", " +
                "inUse=" + inUse + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + "}";
    }
}
