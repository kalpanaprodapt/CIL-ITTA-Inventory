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
 * Sub Interface - Port and Interface domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_sub_interface",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_sub_interface_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_sub_interface_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_sub_interface_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_sub_interface_ext", columnList = "external_ref")
        })
public class SubInterface extends BaseEntity {

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

    @Column(name = "interface_id", nullable = false)
    private UUID interfaceId;

    @Column(name = "vlan_tag", nullable = false)
    private Integer vlanTag;

    @Column(name = "encapsulation", nullable = true, length = 255)
    private String encapsulation;

    @Column(name = "ipv4_address", nullable = true, length = 255)
    private String ipv4Address;

    @Column(name = "ipv6_address", nullable = true, length = 255)
    private String ipv6Address;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    public SubInterface() {
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

    public UUID getInterfaceId() { return interfaceId; }
    public void setInterfaceId(UUID interfaceId) { this.interfaceId = interfaceId; }

    public Integer getVlanTag() { return vlanTag; }
    public void setVlanTag(Integer vlanTag) { this.vlanTag = vlanTag; }

    public String getEncapsulation() { return encapsulation; }
    public void setEncapsulation(String encapsulation) { this.encapsulation = encapsulation; }

    public String getIpv4Address() { return ipv4Address; }
    public void setIpv4Address(String ipv4Address) { this.ipv4Address = ipv4Address; }

    public String getIpv6Address() { return ipv6Address; }
    public void setIpv6Address(String ipv6Address) { this.ipv6Address = ipv6Address; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubInterface other)) {
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
        return "SubInterface{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "interfaceId=" + interfaceId + ", " +
                "vlanTag=" + vlanTag + ", " +
                "encapsulation=" + encapsulation + ", " +
                "ipv4Address=" + ipv4Address + ", " +
                "ipv6Address=" + ipv6Address + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + "}";
    }
}
