package com.uniti.cil.itta.inventory.virtual.model;

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
 * Virtual Machine - Virtual and Cloud domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_virtual_machine",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_virtual_machine_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_virtual_machine_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_virtual_machine_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_virtual_machine_ext", columnList = "external_ref")
        })
public class VirtualMachine extends BaseEntity {

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

    @Column(name = "hypervisor_id", nullable = false)
    private UUID hypervisorId;

    @Column(name = "vm_name", nullable = false, length = 255)
    private String vmName;

    @Column(name = "vcpu", nullable = false)
    private Integer vcpu;

    @Column(name = "memory_gb", nullable = false)
    private Integer memoryGb;

    @Column(name = "disk_gb", nullable = true)
    private Integer diskGb;

    @Enumerated(EnumType.STRING)
    @Column(name = "power_state", nullable = true, length = 40)
    private VirtualMachinePowerState powerState;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private VirtualMachineCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    public VirtualMachine() {
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

    public UUID getHypervisorId() { return hypervisorId; }
    public void setHypervisorId(UUID hypervisorId) { this.hypervisorId = hypervisorId; }

    public String getVmName() { return vmName; }
    public void setVmName(String vmName) { this.vmName = vmName; }

    public Integer getVcpu() { return vcpu; }
    public void setVcpu(Integer vcpu) { this.vcpu = vcpu; }

    public Integer getMemoryGb() { return memoryGb; }
    public void setMemoryGb(Integer memoryGb) { this.memoryGb = memoryGb; }

    public Integer getDiskGb() { return diskGb; }
    public void setDiskGb(Integer diskGb) { this.diskGb = diskGb; }

    public VirtualMachinePowerState getPowerState() { return powerState; }
    public void setPowerState(VirtualMachinePowerState powerState) { this.powerState = powerState; }

    public VirtualMachineCriticality getCriticality() { return criticality; }
    public void setCriticality(VirtualMachineCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VirtualMachine other)) {
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
        return "VirtualMachine{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "hypervisorId=" + hypervisorId + ", " +
                "vmName=" + vmName + ", " +
                "vcpu=" + vcpu + ", " +
                "memoryGb=" + memoryGb + ", " +
                "diskGb=" + diskGb + ", " +
                "powerState=" + powerState + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + "}";
    }
}
