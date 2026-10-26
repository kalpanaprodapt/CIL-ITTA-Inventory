package com.uniti.cil.itta.inventory.capacity.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Utilization Sample; null properties are left unchanged. */
public class UtilizationSampleUpdateRequest {

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
    private String targetRef;
    @Size(max = 255)
    private String metric;
    private Instant sampledAt;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal valuePct;
    private Boolean peak;
    private UtilizationSampleLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private UtilizationSampleCriticality criticality;
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
    public String getTargetRef() { return targetRef; }
    public void setTargetRef(String targetRef) { this.targetRef = targetRef; }
    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }
    public Instant getSampledAt() { return sampledAt; }
    public void setSampledAt(Instant sampledAt) { this.sampledAt = sampledAt; }
    public BigDecimal getValuePct() { return valuePct; }
    public void setValuePct(BigDecimal valuePct) { this.valuePct = valuePct; }
    public Boolean getPeak() { return peak; }
    public void setPeak(Boolean peak) { this.peak = peak; }
    public UtilizationSampleLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(UtilizationSampleLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public UtilizationSampleCriticality getCriticality() { return criticality; }
    public void setCriticality(UtilizationSampleCriticality criticality) { this.criticality = criticality; }}
