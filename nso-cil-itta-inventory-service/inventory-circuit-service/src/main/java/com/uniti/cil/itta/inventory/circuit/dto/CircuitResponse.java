package com.uniti.cil.itta.inventory.circuit.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Read model returned by the Circuit API. */
public class CircuitResponse {

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
    private String circuitId;
    private CircuitCircuitType circuitType;
    private Long bandwidthMbps;
    private UUID providerId;
    private UUID aSiteId;
    private UUID zSiteId;
    private String labels;
    private CircuitLifecycleStage lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;

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
    public String getCircuitId() { return circuitId; }
    public void setCircuitId(String circuitId) { this.circuitId = circuitId; }
    public CircuitCircuitType getCircuitType() { return circuitType; }
    public void setCircuitType(CircuitCircuitType circuitType) { this.circuitType = circuitType; }
    public Long getBandwidthMbps() { return bandwidthMbps; }
    public void setBandwidthMbps(Long bandwidthMbps) { this.bandwidthMbps = bandwidthMbps; }
    public UUID getProviderId() { return providerId; }
    public void setProviderId(UUID providerId) { this.providerId = providerId; }
    public UUID getASiteId() { return aSiteId; }
    public void setASiteId(UUID aSiteId) { this.aSiteId = aSiteId; }
    public UUID getZSiteId() { return zSiteId; }
    public void setZSiteId(UUID zSiteId) { this.zSiteId = zSiteId; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public CircuitLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(CircuitLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
