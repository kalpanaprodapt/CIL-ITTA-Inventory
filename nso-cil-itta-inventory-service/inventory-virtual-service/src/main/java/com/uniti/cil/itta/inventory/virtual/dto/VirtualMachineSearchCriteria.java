package com.uniti.cil.itta.inventory.virtual.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Virtual Machine search endpoint. */
public class VirtualMachineSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID hypervisorId;
    private String vmName;
    private Integer vcpuMin;
    private Integer vcpuMax;
    private Integer memoryGbMin;
    private Integer memoryGbMax;
    private Integer diskGbMin;
    private Integer diskGbMax;
    private List<VirtualMachinePowerState> powerState;
    private List<VirtualMachineCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;

    public String getQ() { return q; }
    public void setQ(String q) { this.q = q; }
    public List<LifecycleStatus> getStatus() { return status; }
    public void setStatus(List<LifecycleStatus> status) { this.status = status; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public UUID getHypervisorId() { return hypervisorId; }
    public void setHypervisorId(UUID hypervisorId) { this.hypervisorId = hypervisorId; }
    public String getVmName() { return vmName; }
    public void setVmName(String vmName) { this.vmName = vmName; }
    public Integer getVcpuMin() { return vcpuMin; }
    public void setVcpuMin(Integer vcpuMin) { this.vcpuMin = vcpuMin; }
    public Integer getVcpuMax() { return vcpuMax; }
    public void setVcpuMax(Integer vcpuMax) { this.vcpuMax = vcpuMax; }
    public Integer getMemoryGbMin() { return memoryGbMin; }
    public void setMemoryGbMin(Integer memoryGbMin) { this.memoryGbMin = memoryGbMin; }
    public Integer getMemoryGbMax() { return memoryGbMax; }
    public void setMemoryGbMax(Integer memoryGbMax) { this.memoryGbMax = memoryGbMax; }
    public Integer getDiskGbMin() { return diskGbMin; }
    public void setDiskGbMin(Integer diskGbMin) { this.diskGbMin = diskGbMin; }
    public Integer getDiskGbMax() { return diskGbMax; }
    public void setDiskGbMax(Integer diskGbMax) { this.diskGbMax = diskGbMax; }
    public List<VirtualMachinePowerState> getPowerState() { return powerState; }
    public void setPowerState(List<VirtualMachinePowerState> powerState) { this.powerState = powerState; }
    public List<VirtualMachineCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<VirtualMachineCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }}
