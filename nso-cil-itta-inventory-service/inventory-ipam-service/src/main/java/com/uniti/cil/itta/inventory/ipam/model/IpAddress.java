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
 * Ip Address - IP Management domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_ip_address",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_ip_address_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_ip_address_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_ip_address_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_ip_address_ext", columnList = "external_ref")
        })
public class IpAddress extends BaseEntity {

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

    @Column(name = "subnet_id", nullable = false)
    private UUID subnetId;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "assigned_to_id", nullable = true)
    private UUID assignedToId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_type", nullable = true, length = 40)
    private IpAddressAssignmentType assignmentType;

    @Column(name = "dns_name", nullable = true, length = 255)
    private String dnsName;

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

    public IpAddress() {
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

    public UUID getSubnetId() { return subnetId; }
    public void setSubnetId(UUID subnetId) { this.subnetId = subnetId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public UUID getAssignedToId() { return assignedToId; }
    public void setAssignedToId(UUID assignedToId) { this.assignedToId = assignedToId; }

    public IpAddressAssignmentType getAssignmentType() { return assignmentType; }
    public void setAssignmentType(IpAddressAssignmentType assignmentType) { this.assignmentType = assignmentType; }

    public String getDnsName() { return dnsName; }
    public void setDnsName(String dnsName) { this.dnsName = dnsName; }

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
        if (!(o instanceof IpAddress other)) {
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
        return "IpAddress{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "subnetId=" + subnetId + ", " +
                "address=" + address + ", " +
                "assignedToId=" + assignedToId + ", " +
                "assignmentType=" + assignmentType + ", " +
                "dnsName=" + dnsName + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + "}";
    }
}
