package com.uniti.cil.itta.inventory.cabling.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Patch Port search endpoint. */
public class PatchPortSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID panelId;
    private Integer portNumberMin;
    private Integer portNumberMax;
    private UUID connectedCableId;
    private String polarity;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<PatchPortLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;

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
    public UUID getPanelId() { return panelId; }
    public void setPanelId(UUID panelId) { this.panelId = panelId; }
    public Integer getPortNumberMin() { return portNumberMin; }
    public void setPortNumberMin(Integer portNumberMin) { this.portNumberMin = portNumberMin; }
    public Integer getPortNumberMax() { return portNumberMax; }
    public void setPortNumberMax(Integer portNumberMax) { this.portNumberMax = portNumberMax; }
    public UUID getConnectedCableId() { return connectedCableId; }
    public void setConnectedCableId(UUID connectedCableId) { this.connectedCableId = connectedCableId; }
    public String getPolarity() { return polarity; }
    public void setPolarity(String polarity) { this.polarity = polarity; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<PatchPortLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<PatchPortLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
