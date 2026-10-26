package com.uniti.cil.itta.inventory.ipam.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Ip Pool search endpoint. */
public class IpPoolSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID subnetId;
    private String rangeStart;
    private String rangeEnd;
    private List<IpPoolPoolType> poolType;
    private Integer leaseSecondsMin;
    private Integer leaseSecondsMax;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<IpPoolLifecycleStage> lifecycleStage;
    private String sourceSystem;

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
    public UUID getSubnetId() { return subnetId; }
    public void setSubnetId(UUID subnetId) { this.subnetId = subnetId; }
    public String getRangeStart() { return rangeStart; }
    public void setRangeStart(String rangeStart) { this.rangeStart = rangeStart; }
    public String getRangeEnd() { return rangeEnd; }
    public void setRangeEnd(String rangeEnd) { this.rangeEnd = rangeEnd; }
    public List<IpPoolPoolType> getPoolType() { return poolType; }
    public void setPoolType(List<IpPoolPoolType> poolType) { this.poolType = poolType; }
    public Integer getLeaseSecondsMin() { return leaseSecondsMin; }
    public void setLeaseSecondsMin(Integer leaseSecondsMin) { this.leaseSecondsMin = leaseSecondsMin; }
    public Integer getLeaseSecondsMax() { return leaseSecondsMax; }
    public void setLeaseSecondsMax(Integer leaseSecondsMax) { this.leaseSecondsMax = leaseSecondsMax; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<IpPoolLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<IpPoolLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
