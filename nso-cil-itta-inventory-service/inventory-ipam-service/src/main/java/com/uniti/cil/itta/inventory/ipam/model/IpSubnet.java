package com.uniti.cil.itta.inventory.ipam.model;

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
 * Ip Subnet - IP Management domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_ip_subnet",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_ip_subnet_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_ip_subnet_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_ip_subnet_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_ip_subnet_ext", columnList = "external_ref")
        })
public class IpSubnet extends BaseEntity {

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

    @Column(name = "cidr", nullable = false, length = 255)
    private String cidr;

    @Enumerated(EnumType.STRING)
    @Column(name = "ip_version", nullable = true, length = 40)
    private IpSubnetIpVersion ipVersion;

    @Column(name = "vrf_id", nullable = true)
    private UUID vrfId;

    @Column(name = "gateway", nullable = true, length = 255)
    private String gateway;

    @Column(name = "vlan_id", nullable = true)
    private UUID vlanId;

    @Column(name = "utilization_pct", nullable = true, precision = 18, scale = 4)
    private BigDecimal utilizationPct;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private IpSubnetCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    public IpSubnet() {
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

    public String getCidr() { return cidr; }
    public void setCidr(String cidr) { this.cidr = cidr; }

    public IpSubnetIpVersion getIpVersion() { return ipVersion; }
    public void setIpVersion(IpSubnetIpVersion ipVersion) { this.ipVersion = ipVersion; }

    public UUID getVrfId() { return vrfId; }
    public void setVrfId(UUID vrfId) { this.vrfId = vrfId; }

    public String getGateway() { return gateway; }
    public void setGateway(String gateway) { this.gateway = gateway; }

    public UUID getVlanId() { return vlanId; }
    public void setVlanId(UUID vlanId) { this.vlanId = vlanId; }

    public BigDecimal getUtilizationPct() { return utilizationPct; }
    public void setUtilizationPct(BigDecimal utilizationPct) { this.utilizationPct = utilizationPct; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public IpSubnetCriticality getCriticality() { return criticality; }
    public void setCriticality(IpSubnetCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof IpSubnet other)) {
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
        return "IpSubnet{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "cidr=" + cidr + ", " +
                "ipVersion=" + ipVersion + ", " +
                "vrfId=" + vrfId + ", " +
                "gateway=" + gateway + ", " +
                "vlanId=" + vlanId + ", " +
                "utilizationPct=" + utilizationPct + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + "}";
    }
}
