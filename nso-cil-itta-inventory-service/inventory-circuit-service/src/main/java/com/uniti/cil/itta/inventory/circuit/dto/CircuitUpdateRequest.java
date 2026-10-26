package com.uniti.cil.itta.inventory.circuit.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Circuit; null properties are left unchanged. */
public class CircuitUpdateRequest {

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
    @Size(max = 255)
    private String circuitId;
    private CircuitCircuitType circuitType;
    private Long bandwidthMbps;
    private UUID providerId;
    private UUID aSiteId;
    private UUID zSiteId;
    @Size(max = 255)
    private String labels;
    private CircuitLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
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
