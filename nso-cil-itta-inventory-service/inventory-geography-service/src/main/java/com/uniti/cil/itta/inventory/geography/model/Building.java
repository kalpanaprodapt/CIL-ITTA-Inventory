package com.uniti.cil.itta.inventory.geography.model;

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
 * Building - Geography domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_building",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_building_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_building_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_building_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_building_ext", columnList = "external_ref")
        })
public class Building extends BaseEntity {

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

    @Column(name = "site_id", nullable = false)
    private UUID siteId;

    @Column(name = "floors", nullable = true)
    private Integer floors;

    @Column(name = "year_built", nullable = true)
    private Integer yearBuilt;

    @Enumerated(EnumType.STRING)
    @Column(name = "ownership", nullable = true, length = 40)
    private BuildingOwnership ownership;

    @Column(name = "lease_expiry", nullable = true)
    private LocalDate leaseExpiry;

    @Column(name = "security_level", nullable = true)
    private Integer securityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private BuildingLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private BuildingCriticality criticality;

    public Building() {
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

    public UUID getSiteId() { return siteId; }
    public void setSiteId(UUID siteId) { this.siteId = siteId; }

    public Integer getFloors() { return floors; }
    public void setFloors(Integer floors) { this.floors = floors; }

    public Integer getYearBuilt() { return yearBuilt; }
    public void setYearBuilt(Integer yearBuilt) { this.yearBuilt = yearBuilt; }

    public BuildingOwnership getOwnership() { return ownership; }
    public void setOwnership(BuildingOwnership ownership) { this.ownership = ownership; }

    public LocalDate getLeaseExpiry() { return leaseExpiry; }
    public void setLeaseExpiry(LocalDate leaseExpiry) { this.leaseExpiry = leaseExpiry; }

    public Integer getSecurityLevel() { return securityLevel; }
    public void setSecurityLevel(Integer securityLevel) { this.securityLevel = securityLevel; }

    public BuildingLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(BuildingLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public BuildingCriticality getCriticality() { return criticality; }
    public void setCriticality(BuildingCriticality criticality) { this.criticality = criticality; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Building other)) {
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
        return "Building{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "siteId=" + siteId + ", " +
                "floors=" + floors + ", " +
                "yearBuilt=" + yearBuilt + ", " +
                "ownership=" + ownership + ", " +
                "leaseExpiry=" + leaseExpiry + ", " +
                "securityLevel=" + securityLevel + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + "}";
    }
}
