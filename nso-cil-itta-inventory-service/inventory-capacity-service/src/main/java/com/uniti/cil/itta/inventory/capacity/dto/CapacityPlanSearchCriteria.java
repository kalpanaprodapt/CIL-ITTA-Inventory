package com.uniti.cil.itta.inventory.capacity.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Capacity Plan search endpoint. */
public class CapacityPlanSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String planName;
    private Integer horizonMonthsMin;
    private Integer horizonMonthsMax;
    private BigDecimal targetUtilizationPctMin;
    private BigDecimal targetUtilizationPctMax;
    private String owner;
    private LocalDate approvedOnFrom;
    private LocalDate approvedOnTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<CapacityPlanLifecycleStage> lifecycleStage;
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
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public Integer getHorizonMonthsMin() { return horizonMonthsMin; }
    public void setHorizonMonthsMin(Integer horizonMonthsMin) { this.horizonMonthsMin = horizonMonthsMin; }
    public Integer getHorizonMonthsMax() { return horizonMonthsMax; }
    public void setHorizonMonthsMax(Integer horizonMonthsMax) { this.horizonMonthsMax = horizonMonthsMax; }
    public BigDecimal getTargetUtilizationPctMin() { return targetUtilizationPctMin; }
    public void setTargetUtilizationPctMin(BigDecimal targetUtilizationPctMin) { this.targetUtilizationPctMin = targetUtilizationPctMin; }
    public BigDecimal getTargetUtilizationPctMax() { return targetUtilizationPctMax; }
    public void setTargetUtilizationPctMax(BigDecimal targetUtilizationPctMax) { this.targetUtilizationPctMax = targetUtilizationPctMax; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public LocalDate getApprovedOnFrom() { return approvedOnFrom; }
    public void setApprovedOnFrom(LocalDate approvedOnFrom) { this.approvedOnFrom = approvedOnFrom; }
    public LocalDate getApprovedOnTo() { return approvedOnTo; }
    public void setApprovedOnTo(LocalDate approvedOnTo) { this.approvedOnTo = approvedOnTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<CapacityPlanLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<CapacityPlanLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
