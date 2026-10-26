package com.uniti.cil.itta.inventory.vendor.model;

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
 * Hardware Model - Vendor and Model domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_hardware_model",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_hardware_model_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_hardware_model_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_hardware_model_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_hardware_model_ext", columnList = "external_ref")
        })
public class HardwareModel extends BaseEntity {

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

    @Column(name = "vendor_id", nullable = false)
    private UUID vendorId;

    @Column(name = "model_number", nullable = false, length = 255)
    private String modelNumber;

    @Column(name = "form_factor", nullable = true, length = 255)
    private String formFactor;

    @Column(name = "power_watts", nullable = true)
    private Integer powerWatts;

    @Column(name = "eol_date", nullable = true)
    private LocalDate eolDate;

    @Column(name = "eos_date", nullable = true)
    private LocalDate eosDate;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private HardwareModelLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public HardwareModel() {
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

    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }

    public String getModelNumber() { return modelNumber; }
    public void setModelNumber(String modelNumber) { this.modelNumber = modelNumber; }

    public String getFormFactor() { return formFactor; }
    public void setFormFactor(String formFactor) { this.formFactor = formFactor; }

    public Integer getPowerWatts() { return powerWatts; }
    public void setPowerWatts(Integer powerWatts) { this.powerWatts = powerWatts; }

    public LocalDate getEolDate() { return eolDate; }
    public void setEolDate(LocalDate eolDate) { this.eolDate = eolDate; }

    public LocalDate getEosDate() { return eosDate; }
    public void setEosDate(LocalDate eosDate) { this.eosDate = eosDate; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public HardwareModelLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(HardwareModelLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

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
        if (!(o instanceof HardwareModel other)) {
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
        return "HardwareModel{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "vendorId=" + vendorId + ", " +
                "modelNumber=" + modelNumber + ", " +
                "formFactor=" + formFactor + ", " +
                "powerWatts=" + powerWatts + ", " +
                "eolDate=" + eolDate + ", " +
                "eosDate=" + eosDate + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + "}";
    }
}
