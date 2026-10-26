package com.uniti.cil.itta.inventory.virtual.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Hypervisor search endpoint. */
public class HypervisorSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID hostDeviceId;
    private List<HypervisorHypervisorType> hypervisorType;
    private String hvVersion;
    private Integer vcpuCapacityMin;
    private Integer vcpuCapacityMax;
    private Integer memoryGbCapacityMin;
    private Integer memoryGbCapacityMax;
    private String region;
    private String owner;
    private List<HypervisorCriticality> criticality;
    private String costCenter;

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
    public UUID getHostDeviceId() { return hostDeviceId; }
    public void setHostDeviceId(UUID hostDeviceId) { this.hostDeviceId = hostDeviceId; }
    public List<HypervisorHypervisorType> getHypervisorType() { return hypervisorType; }
    public void setHypervisorType(List<HypervisorHypervisorType> hypervisorType) { this.hypervisorType = hypervisorType; }
    public String getHvVersion() { return hvVersion; }
    public void setHvVersion(String hvVersion) { this.hvVersion = hvVersion; }
    public Integer getVcpuCapacityMin() { return vcpuCapacityMin; }
    public void setVcpuCapacityMin(Integer vcpuCapacityMin) { this.vcpuCapacityMin = vcpuCapacityMin; }
    public Integer getVcpuCapacityMax() { return vcpuCapacityMax; }
    public void setVcpuCapacityMax(Integer vcpuCapacityMax) { this.vcpuCapacityMax = vcpuCapacityMax; }
    public Integer getMemoryGbCapacityMin() { return memoryGbCapacityMin; }
    public void setMemoryGbCapacityMin(Integer memoryGbCapacityMin) { this.memoryGbCapacityMin = memoryGbCapacityMin; }
    public Integer getMemoryGbCapacityMax() { return memoryGbCapacityMax; }
    public void setMemoryGbCapacityMax(Integer memoryGbCapacityMax) { this.memoryGbCapacityMax = memoryGbCapacityMax; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<HypervisorCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<HypervisorCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
