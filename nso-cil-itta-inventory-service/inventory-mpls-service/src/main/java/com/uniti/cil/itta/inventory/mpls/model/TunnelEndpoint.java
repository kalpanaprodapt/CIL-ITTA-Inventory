package com.uniti.cil.itta.inventory.mpls.model;

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
 * Tunnel Endpoint - MPLS and VPN domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_tunnel_endpoint",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_tunnel_endpoint_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_tunnel_endpoint_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_tunnel_endpoint_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_tunnel_endpoint_ext", columnList = "external_ref")
        })
public class TunnelEndpoint extends BaseEntity {

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

    @Column(name = "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "tunnel_name", nullable = false, length = 255)
    private String tunnelName;

    @Enumerated(EnumType.STRING)
    @Column(name = "tunnel_type", nullable = true, length = 40)
    private TunnelEndpointTunnelType tunnelType;

    @Column(name = "source_ip", nullable = true, length = 255)
    private String sourceIp;

    @Column(name = "destination_ip", nullable = true, length = 255)
    private String destinationIp;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private TunnelEndpointLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    public TunnelEndpoint() {
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

    public UUID getDeviceId() { return deviceId; }
    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }

    public String getTunnelName() { return tunnelName; }
    public void setTunnelName(String tunnelName) { this.tunnelName = tunnelName; }

    public TunnelEndpointTunnelType getTunnelType() { return tunnelType; }
    public void setTunnelType(TunnelEndpointTunnelType tunnelType) { this.tunnelType = tunnelType; }

    public String getSourceIp() { return sourceIp; }
    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }

    public String getDestinationIp() { return destinationIp; }
    public void setDestinationIp(String destinationIp) { this.destinationIp = destinationIp; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public TunnelEndpointLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(TunnelEndpointLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

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
        if (!(o instanceof TunnelEndpoint other)) {
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
        return "TunnelEndpoint{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "deviceId=" + deviceId + ", " +
                "tunnelName=" + tunnelName + ", " +
                "tunnelType=" + tunnelType + ", " +
                "sourceIp=" + sourceIp + ", " +
                "destinationIp=" + destinationIp + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + "}";
    }
}
