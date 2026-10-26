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
 * Antenna - Radio and Mobile domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_antenna",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_antenna_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_antenna_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_antenna_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_antenna_ext", columnList = "external_ref")
        })
public class Antenna extends BaseEntity {

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

    @Column(name = "sector_id", nullable = false)
    private UUID sectorId;

    @Column(name = "model", nullable = true, length = 255)
    private String model;

    @Column(name = "height_m", nullable = true, precision = 18, scale = 4)
    private BigDecimal heightM;

    @Column(name = "azimuth_deg", nullable = true, precision = 18, scale = 4)
    private BigDecimal azimuthDeg;

    @Column(name = "tilt_deg", nullable = true, precision = 18, scale = 4)
    private BigDecimal tiltDeg;

    @Column(name = "gain_dbi", nullable = true, precision = 18, scale = 4)
    private BigDecimal gainDbi;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private AntennaCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    public Antenna() {
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

    public UUID getSectorId() { return sectorId; }
    public void setSectorId(UUID sectorId) { this.sectorId = sectorId; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public BigDecimal getHeightM() { return heightM; }
    public void setHeightM(BigDecimal heightM) { this.heightM = heightM; }

    public BigDecimal getAzimuthDeg() { return azimuthDeg; }
    public void setAzimuthDeg(BigDecimal azimuthDeg) { this.azimuthDeg = azimuthDeg; }

    public BigDecimal getTiltDeg() { return tiltDeg; }
    public void setTiltDeg(BigDecimal tiltDeg) { this.tiltDeg = tiltDeg; }

    public BigDecimal getGainDbi() { return gainDbi; }
    public void setGainDbi(BigDecimal gainDbi) { this.gainDbi = gainDbi; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public AntennaCriticality getCriticality() { return criticality; }
    public void setCriticality(AntennaCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Antenna other)) {
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
        return "Antenna{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "sectorId=" + sectorId + ", " +
                "model=" + model + ", " +
                "heightM=" + heightM + ", " +
                "azimuthDeg=" + azimuthDeg + ", " +
                "tiltDeg=" + tiltDeg + ", " +
                "gainDbi=" + gainDbi + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + "}";
    }
}
