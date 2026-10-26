package com.uniti.cil.itta.inventory.optical.model;

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
 * Otn Link - Optical domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_otn_link",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_otn_link_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_otn_link_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_otn_link_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_otn_link_ext", columnList = "external_ref")
        })
public class OtnLink extends BaseEntity {

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

    @Column(name = "a_node_id", nullable = false)
    private UUID aNodeId;

    @Column(name = "z_node_id", nullable = false)
    private UUID zNodeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "odu", nullable = true, length = 40)
    private OtnLinkOdu odu;

    @Column(name = "latency_us", nullable = true, precision = 18, scale = 4)
    private BigDecimal latencyUs;

    @Column(name = "protection", nullable = true, length = 255)
    private String protection;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private OtnLinkLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    public OtnLink() {
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

    public UUID getANodeId() { return aNodeId; }
    public void setANodeId(UUID aNodeId) { this.aNodeId = aNodeId; }

    public UUID getZNodeId() { return zNodeId; }
    public void setZNodeId(UUID zNodeId) { this.zNodeId = zNodeId; }

    public OtnLinkOdu getOdu() { return odu; }
    public void setOdu(OtnLinkOdu odu) { this.odu = odu; }

    public BigDecimal getLatencyUs() { return latencyUs; }
    public void setLatencyUs(BigDecimal latencyUs) { this.latencyUs = latencyUs; }

    public String getProtection() { return protection; }
    public void setProtection(String protection) { this.protection = protection; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public OtnLinkLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(OtnLinkLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OtnLink other)) {
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
        return "OtnLink{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "aNodeId=" + aNodeId + ", " +
                "zNodeId=" + zNodeId + ", " +
                "odu=" + odu + ", " +
                "latencyUs=" + latencyUs + ", " +
                "protection=" + protection + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + "}";
    }
}
