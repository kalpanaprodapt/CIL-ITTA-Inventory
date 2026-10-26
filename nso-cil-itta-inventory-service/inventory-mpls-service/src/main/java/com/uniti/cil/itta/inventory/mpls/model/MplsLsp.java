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
 * Mpls Lsp - MPLS and VPN domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_mpls_lsp",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_mpls_lsp_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_mpls_lsp_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_mpls_lsp_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_mpls_lsp_ext", columnList = "external_ref")
        })
public class MplsLsp extends BaseEntity {

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

    @Column(name = "lsp_name", nullable = false, length = 255)
    private String lspName;

    @Column(name = "head_end_id", nullable = false)
    private UUID headEndId;

    @Column(name = "tail_end_id", nullable = false)
    private UUID tailEndId;

    @Column(name = "bandwidth_mbps", nullable = true)
    private Long bandwidthMbps;

    @Column(name = "setup_priority", nullable = true)
    private Integer setupPriority;

    @Enumerated(EnumType.STRING)
    @Column(name = "path_type", nullable = true, length = 40)
    private MplsLspPathType pathType;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private MplsLspCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    public MplsLsp() {
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

    public String getLspName() { return lspName; }
    public void setLspName(String lspName) { this.lspName = lspName; }

    public UUID getHeadEndId() { return headEndId; }
    public void setHeadEndId(UUID headEndId) { this.headEndId = headEndId; }

    public UUID getTailEndId() { return tailEndId; }
    public void setTailEndId(UUID tailEndId) { this.tailEndId = tailEndId; }

    public Long getBandwidthMbps() { return bandwidthMbps; }
    public void setBandwidthMbps(Long bandwidthMbps) { this.bandwidthMbps = bandwidthMbps; }

    public Integer getSetupPriority() { return setupPriority; }
    public void setSetupPriority(Integer setupPriority) { this.setupPriority = setupPriority; }

    public MplsLspPathType getPathType() { return pathType; }
    public void setPathType(MplsLspPathType pathType) { this.pathType = pathType; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public MplsLspCriticality getCriticality() { return criticality; }
    public void setCriticality(MplsLspCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof MplsLsp other)) {
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
        return "MplsLsp{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "lspName=" + lspName + ", " +
                "headEndId=" + headEndId + ", " +
                "tailEndId=" + tailEndId + ", " +
                "bandwidthMbps=" + bandwidthMbps + ", " +
                "setupPriority=" + setupPriority + ", " +
                "pathType=" + pathType + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + "}";
    }
}
