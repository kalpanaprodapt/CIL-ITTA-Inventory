package com.uniti.cil.itta.inventory.circuit.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Circuit search endpoint. */
public class CircuitSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String circuitId;
    private List<CircuitCircuitType> circuitType;
    private Long bandwidthMbpsMin;
    private Long bandwidthMbpsMax;
    private UUID providerId;
    private UUID aSiteId;
    private UUID zSiteId;
    private String labels;
    private List<CircuitLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;

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
    public String getCircuitId() { return circuitId; }
    public void setCircuitId(String circuitId) { this.circuitId = circuitId; }
    public List<CircuitCircuitType> getCircuitType() { return circuitType; }
    public void setCircuitType(List<CircuitCircuitType> circuitType) { this.circuitType = circuitType; }
    public Long getBandwidthMbpsMin() { return bandwidthMbpsMin; }
    public void setBandwidthMbpsMin(Long bandwidthMbpsMin) { this.bandwidthMbpsMin = bandwidthMbpsMin; }
    public Long getBandwidthMbpsMax() { return bandwidthMbpsMax; }
    public void setBandwidthMbpsMax(Long bandwidthMbpsMax) { this.bandwidthMbpsMax = bandwidthMbpsMax; }
    public UUID getProviderId() { return providerId; }
    public void setProviderId(UUID providerId) { this.providerId = providerId; }
    public UUID getASiteId() { return aSiteId; }
    public void setASiteId(UUID aSiteId) { this.aSiteId = aSiteId; }
    public UUID getZSiteId() { return zSiteId; }
    public void setZSiteId(UUID zSiteId) { this.zSiteId = zSiteId; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<CircuitLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<CircuitLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
