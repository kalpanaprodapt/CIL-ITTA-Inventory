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
 * Radio Unit - Radio and Mobile domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_radio_unit",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_radio_unit_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_radio_unit_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_radio_unit_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_radio_unit_ext", columnList = "external_ref")
        })
public class RadioUnit extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type", nullable = true, length = 40)
    private RadioUnitUnitType unitType;

    @Column(name = "band_mhz", nullable = true)
    private Integer bandMhz;

    @Column(name = "tx_power_dbm", nullable = true, precision = 18, scale = 4)
    private BigDecimal txPowerDbm;

    @Column(name = "serial_number", nullable = true, length = 255)
    private String serialNumber;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private RadioUnitLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    public RadioUnit() {
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

    public RadioUnitUnitType getUnitType() { return unitType; }
    public void setUnitType(RadioUnitUnitType unitType) { this.unitType = unitType; }

    public Integer getBandMhz() { return bandMhz; }
    public void setBandMhz(Integer bandMhz) { this.bandMhz = bandMhz; }

    public BigDecimal getTxPowerDbm() { return txPowerDbm; }
    public void setTxPowerDbm(BigDecimal txPowerDbm) { this.txPowerDbm = txPowerDbm; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public RadioUnitLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(RadioUnitLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RadioUnit other)) {
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
        return "RadioUnit{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "siteId=" + siteId + ", " +
                "unitType=" + unitType + ", " +
                "bandMhz=" + bandMhz + ", " +
                "txPowerDbm=" + txPowerDbm + ", " +
                "serialNumber=" + serialNumber + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + "}";
    }
}
