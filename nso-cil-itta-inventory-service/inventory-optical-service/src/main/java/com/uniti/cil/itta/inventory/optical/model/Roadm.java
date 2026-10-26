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
 * Roadm - Optical domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_roadm",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_roadm_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_roadm_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_roadm_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_roadm_ext", columnList = "external_ref")
        })
public class Roadm extends BaseEntity {

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

    @Column(name = "system_id", nullable = false)
    private UUID systemId;

    @Column(name = "degrees", nullable = false)
    private Integer degrees;

    @Column(name = "colorless", nullable = true)
    private Boolean colorless;

    @Column(name = "directionless", nullable = true)
    private Boolean directionless;

    @Column(name = "contentionless", nullable = true)
    private Boolean contentionless;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private RoadmCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    public Roadm() {
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

    public UUID getSystemId() { return systemId; }
    public void setSystemId(UUID systemId) { this.systemId = systemId; }

    public Integer getDegrees() { return degrees; }
    public void setDegrees(Integer degrees) { this.degrees = degrees; }

    public Boolean getColorless() { return colorless; }
    public void setColorless(Boolean colorless) { this.colorless = colorless; }

    public Boolean getDirectionless() { return directionless; }
    public void setDirectionless(Boolean directionless) { this.directionless = directionless; }

    public Boolean getContentionless() { return contentionless; }
    public void setContentionless(Boolean contentionless) { this.contentionless = contentionless; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public RoadmCriticality getCriticality() { return criticality; }
    public void setCriticality(RoadmCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof Roadm other)) {
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
        return "Roadm{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "systemId=" + systemId + ", " +
                "degrees=" + degrees + ", " +
                "colorless=" + colorless + ", " +
                "directionless=" + directionless + ", " +
                "contentionless=" + contentionless + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + "}";
    }
}
