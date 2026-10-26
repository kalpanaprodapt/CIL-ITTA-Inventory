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

/** Partial update payload for a Hypervisor; null properties are left unchanged. */
public class HypervisorUpdateRequest {

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
    private UUID hostDeviceId;
    private HypervisorHypervisorType hypervisorType;
    @Size(max = 255)
    private String hvVersion;
    private Integer vcpuCapacity;
    private Integer memoryGbCapacity;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private HypervisorCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
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
    public UUID getHostDeviceId() { return hostDeviceId; }
    public void setHostDeviceId(UUID hostDeviceId) { this.hostDeviceId = hostDeviceId; }
    public HypervisorHypervisorType getHypervisorType() { return hypervisorType; }
    public void setHypervisorType(HypervisorHypervisorType hypervisorType) { this.hypervisorType = hypervisorType; }
    public String getHvVersion() { return hvVersion; }
    public void setHvVersion(String hvVersion) { this.hvVersion = hvVersion; }
    public Integer getVcpuCapacity() { return vcpuCapacity; }
    public void setVcpuCapacity(Integer vcpuCapacity) { this.vcpuCapacity = vcpuCapacity; }
    public Integer getMemoryGbCapacity() { return memoryGbCapacity; }
    public void setMemoryGbCapacity(Integer memoryGbCapacity) { this.memoryGbCapacity = memoryGbCapacity; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public HypervisorCriticality getCriticality() { return criticality; }
    public void setCriticality(HypervisorCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }}
