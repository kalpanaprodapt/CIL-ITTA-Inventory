package com.uniti.cil.itta.inventory.virtual.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Virtual Machine; null properties are left unchanged. */
public class VirtualMachineUpdateRequest {

    private Long expectedVersion;

    @Size(max = 255)
    private String code;
    @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    private UUID hypervisorId;
    @Size(max = 255)
    private String vmName;
    private Integer vcpu;
    private Integer memoryGb;
    private Integer diskGb;
    private VirtualMachinePowerState powerState;
    private VirtualMachineCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    public Long getExpectedVersion() { return expectedVersion; }
    public void setExpectedVersion(Long expectedVersion) { this.expectedVersion = expectedVersion; }

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
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }}
