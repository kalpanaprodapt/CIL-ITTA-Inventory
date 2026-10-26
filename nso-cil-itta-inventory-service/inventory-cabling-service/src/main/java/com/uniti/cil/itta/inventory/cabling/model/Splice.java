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
 * Splice - Cabling domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_splice",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_splice_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_splice_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_splice_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_splice_ext", columnList = "external_ref")
        })
public class Splice extends BaseEntity {

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

    @Column(name = "enclosure_id", nullable = false, length = 255)
    private String enclosureId;

    @Column(name = "a_strand_id", nullable = true)
    private UUID aStrandId;

    @Column(name = "z_strand_id", nullable = true)
    private UUID zStrandId;

    @Column(name = "loss_db", nullable = true, precision = 18, scale = 4)
    private BigDecimal lossDb;

    @Column(name = "spliced_on", nullable = true)
    private LocalDate splicedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private SpliceLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private SpliceCriticality criticality;

    public Splice() {
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

    public String getEnclosureId() { return enclosureId; }
    public void setEnclosureId(String enclosureId) { this.enclosureId = enclosureId; }

    public UUID getAStrandId() { return aStrandId; }
    public void setAStrandId(UUID aStrandId) { this.aStrandId = aStrandId; }

    public UUID getZStrandId() { return zStrandId; }
    public void setZStrandId(UUID zStrandId) { this.zStrandId = zStrandId; }

    public BigDecimal getLossDb() { return lossDb; }
    public void setLossDb(BigDecimal lossDb) { this.lossDb = lossDb; }

    public LocalDate getSplicedOn() { return splicedOn; }
    public void setSplicedOn(LocalDate splicedOn) { this.splicedOn = splicedOn; }

    public SpliceLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(SpliceLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public SpliceCriticality getCriticality() { return criticality; }
    public void setCriticality(SpliceCriticality criticality) { this.criticality = criticality; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Splice other)) {
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
        return "Splice{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "enclosureId=" + enclosureId + ", " +
                "aStrandId=" + aStrandId + ", " +
                "zStrandId=" + zStrandId + ", " +
                "lossDb=" + lossDb + ", " +
                "splicedOn=" + splicedOn + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + "}";
    }
}
