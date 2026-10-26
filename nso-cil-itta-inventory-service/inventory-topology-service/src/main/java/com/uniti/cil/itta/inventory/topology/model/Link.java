package com.uniti.cil.itta.inventory.topology.model;

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
 * Link - Topology and Links domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_link",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_link_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_link_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_link_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_link_ext", columnList = "external_ref")
        })
public class Link extends BaseEntity {

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

    @Column(name = "a_interface_id", nullable = false)
    private UUID aInterfaceId;

    @Column(name = "z_interface_id", nullable = false)
    private UUID zInterfaceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "link_type", nullable = true, length = 40)
    private LinkLinkType linkType;

    @Column(name = "speed_mbps", nullable = true)
    private Long speedMbps;

    @Column(name = "discovered_by", nullable = true, length = 255)
    private String discoveredBy;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private LinkCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    public Link() {
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

    public UUID getAInterfaceId() { return aInterfaceId; }
    public void setAInterfaceId(UUID aInterfaceId) { this.aInterfaceId = aInterfaceId; }

    public UUID getZInterfaceId() { return zInterfaceId; }
    public void setZInterfaceId(UUID zInterfaceId) { this.zInterfaceId = zInterfaceId; }

    public LinkLinkType getLinkType() { return linkType; }
    public void setLinkType(LinkLinkType linkType) { this.linkType = linkType; }

    public Long getSpeedMbps() { return speedMbps; }
    public void setSpeedMbps(Long speedMbps) { this.speedMbps = speedMbps; }

    public String getDiscoveredBy() { return discoveredBy; }
    public void setDiscoveredBy(String discoveredBy) { this.discoveredBy = discoveredBy; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public LinkCriticality getCriticality() { return criticality; }
    public void setCriticality(LinkCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Link other)) {
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
        return "Link{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "aInterfaceId=" + aInterfaceId + ", " +
                "zInterfaceId=" + zInterfaceId + ", " +
                "linkType=" + linkType + ", " +
                "speedMbps=" + speedMbps + ", " +
                "discoveredBy=" + discoveredBy + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + "}";
    }
}
