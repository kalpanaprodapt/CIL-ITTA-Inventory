package com.uniti.cil.itta.inventory.port.model;

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
 * Interface - Port and Interface domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_interface",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_interface_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_interface_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_interface_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_interface_ext", columnList = "external_ref")
        })
public class Interface extends BaseEntity {

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

    @Column(name = "port_id", nullable = false)
    private UUID portId;

    @Column(name = "if_name", nullable = false, length = 255)
    private String ifName;

    @Column(name = "if_index", nullable = true)
    private Integer ifIndex;

    @Column(name = "mac_address", nullable = true, length = 255)
    private String macAddress;

    @Column(name = "mtu", nullable = true)
    private Integer mtu;

    @Enumerated(EnumType.STRING)
    @Column(name = "oper_state", nullable = true, length = 40)
    private InterfaceOperState operState;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private InterfaceCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    public Interface() {
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

    public UUID getPortId() { return portId; }
    public void setPortId(UUID portId) { this.portId = portId; }

    public String getIfName() { return ifName; }
    public void setIfName(String ifName) { this.ifName = ifName; }

    public Integer getIfIndex() { return ifIndex; }
    public void setIfIndex(Integer ifIndex) { this.ifIndex = ifIndex; }

    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }

    public Integer getMtu() { return mtu; }
    public void setMtu(Integer mtu) { this.mtu = mtu; }

    public InterfaceOperState getOperState() { return operState; }
    public void setOperState(InterfaceOperState operState) { this.operState = operState; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public InterfaceCriticality getCriticality() { return criticality; }
    public void setCriticality(InterfaceCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof Interface other)) {
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
        return "Interface{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "portId=" + portId + ", " +
                "ifName=" + ifName + ", " +
                "ifIndex=" + ifIndex + ", " +
                "macAddress=" + macAddress + ", " +
                "mtu=" + mtu + ", " +
                "operState=" + operState + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + "}";
    }
}
