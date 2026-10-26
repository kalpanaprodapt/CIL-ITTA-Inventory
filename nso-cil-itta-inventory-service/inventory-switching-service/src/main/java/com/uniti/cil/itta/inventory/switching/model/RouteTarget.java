package com.uniti.cil.itta.inventory.switching.model;

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
 * Route Target - L2 and L3 Routing domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_route_target",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_route_target_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_route_target_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_route_target_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_route_target_ext", columnList = "external_ref")
        })
public class RouteTarget extends BaseEntity {

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

    @Column(name = "asn", nullable = false)
    private Long asn;

    @Column(name = "assigned_number", nullable = false)
    private Long assignedNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "rt_type", nullable = true, length = 40)
    private RouteTargetRtType rtType;

    @Column(name = "vrf_id", nullable = true)
    private UUID vrfId;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private RouteTargetLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private RouteTargetCriticality criticality;

    public RouteTarget() {
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

    public Long getAsn() { return asn; }
    public void setAsn(Long asn) { this.asn = asn; }

    public Long getAssignedNumber() { return assignedNumber; }
    public void setAssignedNumber(Long assignedNumber) { this.assignedNumber = assignedNumber; }

    public RouteTargetRtType getRtType() { return rtType; }
    public void setRtType(RouteTargetRtType rtType) { this.rtType = rtType; }

    public UUID getVrfId() { return vrfId; }
    public void setVrfId(UUID vrfId) { this.vrfId = vrfId; }

    public RouteTargetLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(RouteTargetLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public RouteTargetCriticality getCriticality() { return criticality; }
    public void setCriticality(RouteTargetCriticality criticality) { this.criticality = criticality; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RouteTarget other)) {
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
        return "RouteTarget{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "asn=" + asn + ", " +
                "assignedNumber=" + assignedNumber + ", " +
                "rtType=" + rtType + ", " +
                "vrfId=" + vrfId + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + "}";
    }
}
