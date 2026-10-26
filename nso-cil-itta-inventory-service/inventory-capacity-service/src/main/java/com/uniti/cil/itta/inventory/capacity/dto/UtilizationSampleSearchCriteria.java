package com.uniti.cil.itta.inventory.capacity.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Utilization Sample search endpoint. */
public class UtilizationSampleSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String targetRef;
    private String metric;
    private Instant sampledAtFrom;
    private Instant sampledAtTo;
    private BigDecimal valuePctMin;
    private BigDecimal valuePctMax;
    private Boolean peak;
    private List<UtilizationSampleLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<UtilizationSampleCriticality> criticality;

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
    public String getTargetRef() { return targetRef; }
    public void setTargetRef(String targetRef) { this.targetRef = targetRef; }
    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }
    public Instant getSampledAtFrom() { return sampledAtFrom; }
    public void setSampledAtFrom(Instant sampledAtFrom) { this.sampledAtFrom = sampledAtFrom; }
    public Instant getSampledAtTo() { return sampledAtTo; }
    public void setSampledAtTo(Instant sampledAtTo) { this.sampledAtTo = sampledAtTo; }
    public BigDecimal getValuePctMin() { return valuePctMin; }
    public void setValuePctMin(BigDecimal valuePctMin) { this.valuePctMin = valuePctMin; }
    public BigDecimal getValuePctMax() { return valuePctMax; }
    public void setValuePctMax(BigDecimal valuePctMax) { this.valuePctMax = valuePctMax; }
    public Boolean getPeak() { return peak; }
    public void setPeak(Boolean peak) { this.peak = peak; }
    public List<UtilizationSampleLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<UtilizationSampleLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<UtilizationSampleCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<UtilizationSampleCriticality> criticality) { this.criticality = criticality; }}
