package com.uniti.cil.itta.inventory.service.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Read model returned by the Service Instance API. */
public class ServiceInstanceResponse {

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
    private UUID catalogItemId;
    private UUID customerId;
    private LocalDate activatedOn;
    private Long bandwidthMbps;
    private ServiceInstanceServiceState serviceState;
    private ServiceInstanceLifecycleStage lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private ServiceInstanceCriticality criticality;

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
    public UUID getCatalogItemId() { return catalogItemId; }
    public void setCatalogItemId(UUID catalogItemId) { this.catalogItemId = catalogItemId; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public LocalDate getActivatedOn() { return activatedOn; }
    public void setActivatedOn(LocalDate activatedOn) { this.activatedOn = activatedOn; }
    public Long getBandwidthMbps() { return bandwidthMbps; }
    public void setBandwidthMbps(Long bandwidthMbps) { this.bandwidthMbps = bandwidthMbps; }
    public ServiceInstanceServiceState getServiceState() { return serviceState; }
    public void setServiceState(ServiceInstanceServiceState serviceState) { this.serviceState = serviceState; }
    public ServiceInstanceLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(ServiceInstanceLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public ServiceInstanceCriticality getCriticality() { return criticality; }
    public void setCriticality(ServiceInstanceCriticality criticality) { this.criticality = criticality; }}
