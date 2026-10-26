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
 * Vlan - L2 and L3 Routing domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_vlan",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_vlan_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_vlan_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_vlan_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_vlan_ext", columnList = "external_ref")
        })
public class Vlan extends BaseEntity {

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

    @Column(name = "vlan_id", nullable = false)
    private Integer vlanId;

    @Column(name = "vlan_name", nullable = false, length = 255)
    private String vlanName;

    @Enumerated(EnumType.STRING)
    @Column(name = "vlan_type", nullable = true, length = 40)
    private VlanVlanType vlanType;

    @Column(name = "pool_id", nullable = true)
    private UUID poolId;

    @Column(name = "stp_mode", nullable = true, length = 255)
    private String stpMode;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private VlanLifecycleStage lifecycleStage;

    public Vlan() {
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

    public Integer getVlanId() { return vlanId; }
    public void setVlanId(Integer vlanId) { this.vlanId = vlanId; }

    public String getVlanName() { return vlanName; }
    public void setVlanName(String vlanName) { this.vlanName = vlanName; }

    public VlanVlanType getVlanType() { return vlanType; }
    public void setVlanType(VlanVlanType vlanType) { this.vlanType = vlanType; }

    public UUID getPoolId() { return poolId; }
    public void setPoolId(UUID poolId) { this.poolId = poolId; }

    public String getStpMode() { return stpMode; }
    public void setStpMode(String stpMode) { this.stpMode = stpMode; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public VlanLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(VlanLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vlan other)) {
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
        return "Vlan{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "vlanId=" + vlanId + ", " +
                "vlanName=" + vlanName + ", " +
                "vlanType=" + vlanType + ", " +
                "poolId=" + poolId + ", " +
                "stpMode=" + stpMode + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + "}";
    }
}
