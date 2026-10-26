package com.uniti.cil.itta.inventory.optical.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Otn Link search endpoint. */
public class OtnLinkSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID aNodeId;
    private UUID zNodeId;
    private List<OtnLinkOdu> odu;
    private BigDecimal latencyUsMin;
    private BigDecimal latencyUsMax;
    private String protection;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<OtnLinkLifecycleStage> lifecycleStage;
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
    public UUID getANodeId() { return aNodeId; }
    public void setANodeId(UUID aNodeId) { this.aNodeId = aNodeId; }
    public UUID getZNodeId() { return zNodeId; }
    public void setZNodeId(UUID zNodeId) { this.zNodeId = zNodeId; }
    public List<OtnLinkOdu> getOdu() { return odu; }
    public void setOdu(List<OtnLinkOdu> odu) { this.odu = odu; }
    public BigDecimal getLatencyUsMin() { return latencyUsMin; }
    public void setLatencyUsMin(BigDecimal latencyUsMin) { this.latencyUsMin = latencyUsMin; }
    public BigDecimal getLatencyUsMax() { return latencyUsMax; }
    public void setLatencyUsMax(BigDecimal latencyUsMax) { this.latencyUsMax = latencyUsMax; }
    public String getProtection() { return protection; }
    public void setProtection(String protection) { this.protection = protection; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<OtnLinkLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<OtnLinkLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
