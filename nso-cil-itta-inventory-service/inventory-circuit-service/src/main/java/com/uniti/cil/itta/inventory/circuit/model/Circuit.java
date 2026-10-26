package com.uniti.cil.itta.inventory.circuit.model;

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
 * Circuit - Circuits domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_circuit",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_circuit_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_circuit_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_circuit_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_circuit_ext", columnList = "external_ref")
        })
public class Circuit extends BaseEntity {

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

    @Column(name = "circuit_id", nullable = false, length = 255)
    private String circuitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "circuit_type", nullable = true, length = 40)
    private CircuitCircuitType circuitType;

    @Column(name = "bandwidth_mbps", nullable = false)
    private Long bandwidthMbps;

    @Column(name = "provider_id", nullable = true)
    private UUID providerId;

    @Column(name = "a_site_id", nullable = true)
    private UUID aSiteId;

    @Column(name = "z_site_id", nullable = true)
    private UUID zSiteId;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private CircuitLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public Circuit() {
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

    public String getCircuitId() { return circuitId; }
    public void setCircuitId(String circuitId) { this.circuitId = circuitId; }

    public CircuitCircuitType getCircuitType() { return circuitType; }
    public void setCircuitType(CircuitCircuitType circuitType) { this.circuitType = circuitType; }

    public Long getBandwidthMbps() { return bandwidthMbps; }
    public void setBandwidthMbps(Long bandwidthMbps) { this.bandwidthMbps = bandwidthMbps; }

    public UUID getProviderId() { return providerId; }
    public void setProviderId(UUID providerId) { this.providerId = providerId; }

    public UUID getASiteId() { return aSiteId; }
    public void setASiteId(UUID aSiteId) { this.aSiteId = aSiteId; }

    public UUID getZSiteId() { return zSiteId; }
    public void setZSiteId(UUID zSiteId) { this.zSiteId = zSiteId; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public CircuitLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(CircuitLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

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
        if (!(o instanceof Circuit other)) {
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
        return "Circuit{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "circuitId=" + circuitId + ", " +
                "circuitType=" + circuitType + ", " +
                "bandwidthMbps=" + bandwidthMbps + ", " +
                "providerId=" + providerId + ", " +
                "aSiteId=" + aSiteId + ", " +
                "zSiteId=" + zSiteId + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + "}";
    }
}
