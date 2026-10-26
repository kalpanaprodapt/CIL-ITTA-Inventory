package com.uniti.cil.itta.inventory.topology.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Path Trace search endpoint. */
public class PathTraceSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String sourceRef;
    private String targetRef;
    private Integer hopCountMin;
    private Integer hopCountMax;
    private BigDecimal totalLatencyMsMin;
    private BigDecimal totalLatencyMsMax;
    private String traceMethod;
    private Instant tracedAtFrom;
    private Instant tracedAtTo;
    private List<PathTraceLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<PathTraceCriticality> criticality;

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
    public String getSourceRef() { return sourceRef; }
    public void setSourceRef(String sourceRef) { this.sourceRef = sourceRef; }
    public String getTargetRef() { return targetRef; }
    public void setTargetRef(String targetRef) { this.targetRef = targetRef; }
    public Integer getHopCountMin() { return hopCountMin; }
    public void setHopCountMin(Integer hopCountMin) { this.hopCountMin = hopCountMin; }
    public Integer getHopCountMax() { return hopCountMax; }
    public void setHopCountMax(Integer hopCountMax) { this.hopCountMax = hopCountMax; }
    public BigDecimal getTotalLatencyMsMin() { return totalLatencyMsMin; }
    public void setTotalLatencyMsMin(BigDecimal totalLatencyMsMin) { this.totalLatencyMsMin = totalLatencyMsMin; }
    public BigDecimal getTotalLatencyMsMax() { return totalLatencyMsMax; }
    public void setTotalLatencyMsMax(BigDecimal totalLatencyMsMax) { this.totalLatencyMsMax = totalLatencyMsMax; }
    public String getTraceMethod() { return traceMethod; }
    public void setTraceMethod(String traceMethod) { this.traceMethod = traceMethod; }
    public Instant getTracedAtFrom() { return tracedAtFrom; }
    public void setTracedAtFrom(Instant tracedAtFrom) { this.tracedAtFrom = tracedAtFrom; }
    public Instant getTracedAtTo() { return tracedAtTo; }
    public void setTracedAtTo(Instant tracedAtTo) { this.tracedAtTo = tracedAtTo; }
    public List<PathTraceLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<PathTraceLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<PathTraceCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<PathTraceCriticality> criticality) { this.criticality = criticality; }}
