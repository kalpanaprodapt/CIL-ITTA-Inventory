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
 * Vpn Service - MPLS and VPN domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_vpn_service",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_vpn_service_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_vpn_service_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_vpn_service_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_vpn_service_ext", columnList = "external_ref")
        })
public class VpnService extends BaseEntity {

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

    @Column(name = "vpn_name", nullable = false, length = 255)
    private String vpnName;

    @Enumerated(EnumType.STRING)
    @Column(name = "vpn_type", nullable = true, length = 40)
    private VpnServiceVpnType vpnType;

    @Column(name = "vrf_id", nullable = true)
    private UUID vrfId;

    @Column(name = "customer_id", nullable = true)
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "topology", nullable = true, length = 40)
    private VpnServiceTopology topology;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private VpnServiceCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    public VpnService() {
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

    public String getVpnName() { return vpnName; }
    public void setVpnName(String vpnName) { this.vpnName = vpnName; }

    public VpnServiceVpnType getVpnType() { return vpnType; }
    public void setVpnType(VpnServiceVpnType vpnType) { this.vpnType = vpnType; }

    public UUID getVrfId() { return vrfId; }
    public void setVrfId(UUID vrfId) { this.vrfId = vrfId; }

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }

    public VpnServiceTopology getTopology() { return topology; }
    public void setTopology(VpnServiceTopology topology) { this.topology = topology; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public VpnServiceCriticality getCriticality() { return criticality; }
    public void setCriticality(VpnServiceCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VpnService other)) {
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
        return "VpnService{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "vpnName=" + vpnName + ", " +
                "vpnType=" + vpnType + ", " +
                "vrfId=" + vrfId + ", " +
                "customerId=" + customerId + ", " +
                "topology=" + topology + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + "}";
    }
}
