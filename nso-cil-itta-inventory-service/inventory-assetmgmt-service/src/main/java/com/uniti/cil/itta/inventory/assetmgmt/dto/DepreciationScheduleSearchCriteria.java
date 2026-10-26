package com.uniti.cil.itta.inventory.assetmgmt.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Depreciation Schedule search endpoint. */
public class DepreciationScheduleSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID assetId;
    private List<DepreciationScheduleMethod> method;
    private Integer usefulLifeMonthsMin;
    private Integer usefulLifeMonthsMax;
    private BigDecimal salvageValueMin;
    private BigDecimal salvageValueMax;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<DepreciationScheduleLifecycleStage> lifecycleStage;
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
    public UUID getAssetId() { return assetId; }
    public void setAssetId(UUID assetId) { this.assetId = assetId; }
    public List<DepreciationScheduleMethod> getMethod() { return method; }
    public void setMethod(List<DepreciationScheduleMethod> method) { this.method = method; }
    public Integer getUsefulLifeMonthsMin() { return usefulLifeMonthsMin; }
    public void setUsefulLifeMonthsMin(Integer usefulLifeMonthsMin) { this.usefulLifeMonthsMin = usefulLifeMonthsMin; }
    public Integer getUsefulLifeMonthsMax() { return usefulLifeMonthsMax; }
    public void setUsefulLifeMonthsMax(Integer usefulLifeMonthsMax) { this.usefulLifeMonthsMax = usefulLifeMonthsMax; }
    public BigDecimal getSalvageValueMin() { return salvageValueMin; }
    public void setSalvageValueMin(BigDecimal salvageValueMin) { this.salvageValueMin = salvageValueMin; }
    public BigDecimal getSalvageValueMax() { return salvageValueMax; }
    public void setSalvageValueMax(BigDecimal salvageValueMax) { this.salvageValueMax = salvageValueMax; }
    public LocalDate getStartDateFrom() { return startDateFrom; }
    public void setStartDateFrom(LocalDate startDateFrom) { this.startDateFrom = startDateFrom; }
    public LocalDate getStartDateTo() { return startDateTo; }
    public void setStartDateTo(LocalDate startDateTo) { this.startDateTo = startDateTo; }
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
    public List<DepreciationScheduleLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<DepreciationScheduleLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
