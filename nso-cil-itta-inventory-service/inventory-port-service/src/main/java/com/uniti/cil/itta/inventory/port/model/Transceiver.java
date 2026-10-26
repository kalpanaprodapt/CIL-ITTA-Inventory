package com.uniti.cil.itta.inventory.port.model;

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
 * Transceiver - Port and Interface domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_transceiver",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_transceiver_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_transceiver_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_transceiver_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_transceiver_ext", columnList = "external_ref")
        })
public class Transceiver extends BaseEntity {

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

    @Column(name = "port_id", nullable = true)
    private UUID portId;

    @Enumerated(EnumType.STRING)
    @Column(name = "form_factor", nullable = true, length = 40)
    private TransceiverFormFactor formFactor;

    @Column(name = "wavelength_nm", nullable = true)
    private Integer wavelengthNm;

    @Column(name = "reach_km", nullable = true, precision = 18, scale = 4)
    private BigDecimal reachKm;

    @Column(name = "serial_number", nullable = true, length = 255)
    private String serialNumber;

    @Column(name = "vendor_part", nullable = true, length = 255)
    private String vendorPart;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private TransceiverLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public Transceiver() {
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

    public UUID getPortId() { return portId; }
    public void setPortId(UUID portId) { this.portId = portId; }

    public TransceiverFormFactor getFormFactor() { return formFactor; }
    public void setFormFactor(TransceiverFormFactor formFactor) { this.formFactor = formFactor; }

    public Integer getWavelengthNm() { return wavelengthNm; }
    public void setWavelengthNm(Integer wavelengthNm) { this.wavelengthNm = wavelengthNm; }

    public BigDecimal getReachKm() { return reachKm; }
    public void setReachKm(BigDecimal reachKm) { this.reachKm = reachKm; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getVendorPart() { return vendorPart; }
    public void setVendorPart(String vendorPart) { this.vendorPart = vendorPart; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public TransceiverLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(TransceiverLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

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
        if (!(o instanceof Transceiver other)) {
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
        return "Transceiver{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "portId=" + portId + ", " +
                "formFactor=" + formFactor + ", " +
                "wavelengthNm=" + wavelengthNm + ", " +
                "reachKm=" + reachKm + ", " +
                "serialNumber=" + serialNumber + ", " +
                "vendorPart=" + vendorPart + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + "}";
    }
}
