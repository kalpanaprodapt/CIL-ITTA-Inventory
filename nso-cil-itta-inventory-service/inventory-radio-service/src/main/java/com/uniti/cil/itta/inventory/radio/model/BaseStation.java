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
 * Base Station - Radio and Mobile domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_base_station",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_base_station_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_base_station_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_base_station_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_base_station_ext", columnList = "external_ref")
        })
public class BaseStation extends BaseEntity {

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

    @Column(name = "enb_id", nullable = false)
    private Long enbId;

    @Enumerated(EnumType.STRING)
    @Column(name = "technology", nullable = true, length = 40)
    private BaseStationTechnology technology;

    @Column(name = "sector_count", nullable = true)
    private Integer sectorCount;

    @Column(name = "cell_count", nullable = true)
    private Integer cellCount;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private BaseStationLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public BaseStation() {
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

    public Long getEnbId() { return enbId; }
    public void setEnbId(Long enbId) { this.enbId = enbId; }

    public BaseStationTechnology getTechnology() { return technology; }
    public void setTechnology(BaseStationTechnology technology) { this.technology = technology; }

    public Integer getSectorCount() { return sectorCount; }
    public void setSectorCount(Integer sectorCount) { this.sectorCount = sectorCount; }

    public Integer getCellCount() { return cellCount; }
    public void setCellCount(Integer cellCount) { this.cellCount = cellCount; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public BaseStationLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(BaseStationLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseStation other)) {
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
        return "BaseStation{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "siteId=" + siteId + ", " +
                "enbId=" + enbId + ", " +
                "technology=" + technology + ", " +
                "sectorCount=" + sectorCount + ", " +
                "cellCount=" + cellCount + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + "}";
    }
}
