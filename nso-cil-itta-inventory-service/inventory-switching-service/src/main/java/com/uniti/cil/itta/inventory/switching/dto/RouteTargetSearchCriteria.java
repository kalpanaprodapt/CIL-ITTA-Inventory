package com.uniti.cil.itta.inventory.switching.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Route Target search endpoint. */
public class RouteTargetSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private Long asnMin;
    private Long asnMax;
    private Long assignedNumberMin;
    private Long assignedNumberMax;
    private List<RouteTargetRtType> rtType;
    private UUID vrfId;
    private List<RouteTargetLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<RouteTargetCriticality> criticality;

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
    public Long getAsnMin() { return asnMin; }
    public void setAsnMin(Long asnMin) { this.asnMin = asnMin; }
    public Long getAsnMax() { return asnMax; }
    public void setAsnMax(Long asnMax) { this.asnMax = asnMax; }
    public Long getAssignedNumberMin() { return assignedNumberMin; }
    public void setAssignedNumberMin(Long assignedNumberMin) { this.assignedNumberMin = assignedNumberMin; }
    public Long getAssignedNumberMax() { return assignedNumberMax; }
    public void setAssignedNumberMax(Long assignedNumberMax) { this.assignedNumberMax = assignedNumberMax; }
    public List<RouteTargetRtType> getRtType() { return rtType; }
    public void setRtType(List<RouteTargetRtType> rtType) { this.rtType = rtType; }
    public UUID getVrfId() { return vrfId; }
    public void setVrfId(UUID vrfId) { this.vrfId = vrfId; }
    public List<RouteTargetLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<RouteTargetLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<RouteTargetCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<RouteTargetCriticality> criticality) { this.criticality = criticality; }}
