package com.uniti.cil.itta.inventory.virtual.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Read model returned by the Hypervisor API. */
public class HypervisorResponse {

    private UUID id;
    private String tenantId;
    private Long version;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    private String code;
    private String name;
    private String description;
    private LifecycleStatus status;
    private String externalRef;
    private UUID hostDeviceId;
    private HypervisorHypervisorType hypervisorType;
    private String hvVersion;
    private Integer vcpuCapacity;
    private Integer memoryGbCapacity;
    private String region;
    private String owner;
    private HypervisorCriticality criticality;
    private String costCenter;
    private String notes;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

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
