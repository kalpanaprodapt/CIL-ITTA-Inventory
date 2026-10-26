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
 * Qos Policy - MPLS and VPN domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_qos_policy",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_qos_policy_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_qos_policy_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_qos_policy_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_qos_policy_ext", columnList = "external_ref")
        })
public class QosPolicy extends BaseEntity {

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

    @Column(name = "policy_name", nullable = false, length = 255)
    private String policyName;

    @Column(name = "class_count", nullable = false)
    private Integer classCount;

    @Column(name = "cir_mbps", nullable = true)
    private Long cirMbps;

    @Column(name = "pir_mbps", nullable = true)
    private Long pirMbps;

    @Column(name = "marking_scheme", nullable = true, length = 255)
    private String markingScheme;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private QosPolicyLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public QosPolicy() {
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

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public Integer getClassCount() { return classCount; }
    public void setClassCount(Integer classCount) { this.classCount = classCount; }

    public Long getCirMbps() { return cirMbps; }
    public void setCirMbps(Long cirMbps) { this.cirMbps = cirMbps; }

    public Long getPirMbps() { return pirMbps; }
    public void setPirMbps(Long pirMbps) { this.pirMbps = pirMbps; }

    public String getMarkingScheme() { return markingScheme; }
    public void setMarkingScheme(String markingScheme) { this.markingScheme = markingScheme; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public QosPolicyLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(QosPolicyLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

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
        if (!(o instanceof QosPolicy other)) {
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
        return "QosPolicy{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "policyName=" + policyName + ", " +
                "classCount=" + classCount + ", " +
                "cirMbps=" + cirMbps + ", " +
                "pirMbps=" + pirMbps + ", " +
                "markingScheme=" + markingScheme + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + "}";
    }
}
