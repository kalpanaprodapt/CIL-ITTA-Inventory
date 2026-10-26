package com.uniti.cil.itta.inventory.switching.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Vlan Pool search endpoint. */
public class VlanPoolSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String poolName;
    private Integer rangeStartMin;
    private Integer rangeStartMax;
    private Integer rangeEndMin;
    private Integer rangeEndMax;
    private Integer allocatedMin;
    private Integer allocatedMax;
    private String scope;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<VlanPoolLifecycleStage> lifecycleStage;
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
    public String getPoolName() { return poolName; }
    public void setPoolName(String poolName) { this.poolName = poolName; }
    public Integer getRangeStartMin() { return rangeStartMin; }
    public void setRangeStartMin(Integer rangeStartMin) { this.rangeStartMin = rangeStartMin; }
    public Integer getRangeStartMax() { return rangeStartMax; }
    public void setRangeStartMax(Integer rangeStartMax) { this.rangeStartMax = rangeStartMax; }
    public Integer getRangeEndMin() { return rangeEndMin; }
    public void setRangeEndMin(Integer rangeEndMin) { this.rangeEndMin = rangeEndMin; }
    public Integer getRangeEndMax() { return rangeEndMax; }
    public void setRangeEndMax(Integer rangeEndMax) { this.rangeEndMax = rangeEndMax; }
    public Integer getAllocatedMin() { return allocatedMin; }
    public void setAllocatedMin(Integer allocatedMin) { this.allocatedMin = allocatedMin; }
    public Integer getAllocatedMax() { return allocatedMax; }
    public void setAllocatedMax(Integer allocatedMax) { this.allocatedMax = allocatedMax; }
    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<VlanPoolLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<VlanPoolLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
