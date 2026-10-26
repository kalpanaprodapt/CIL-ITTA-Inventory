package com.uniti.cil.itta.inventory.topology.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Path Trace. */
public class PathTraceCreateRequest {

    @NotBlank @Size(max = 255)
    private String code;
    @NotBlank @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    @NotNull
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    @NotBlank @Size(max = 255)
    private String sourceRef;
    @NotBlank @Size(max = 255)
    private String targetRef;
    private Integer hopCount;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal totalLatencyMs;
    @Size(max = 255)
    private String traceMethod;
    private Instant tracedAt;
    private PathTraceLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private PathTraceCriticality criticality;
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
    public String getSourceRef() { return sourceRef; }
    public void setSourceRef(String sourceRef) { this.sourceRef = sourceRef; }
    public String getTargetRef() { return targetRef; }
    public void setTargetRef(String targetRef) { this.targetRef = targetRef; }
    public Integer getHopCount() { return hopCount; }
    public void setHopCount(Integer hopCount) { this.hopCount = hopCount; }
    public BigDecimal getTotalLatencyMs() { return totalLatencyMs; }
    public void setTotalLatencyMs(BigDecimal totalLatencyMs) { this.totalLatencyMs = totalLatencyMs; }
    public String getTraceMethod() { return traceMethod; }
    public void setTraceMethod(String traceMethod) { this.traceMethod = traceMethod; }
    public Instant getTracedAt() { return tracedAt; }
    public void setTracedAt(Instant tracedAt) { this.tracedAt = tracedAt; }
    public PathTraceLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(PathTraceLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public PathTraceCriticality getCriticality() { return criticality; }
    public void setCriticality(PathTraceCriticality criticality) { this.criticality = criticality; }}
