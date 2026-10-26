package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Radio Unit search endpoint. */
public class RadioUnitSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID siteId;
    private List<RadioUnitUnitType> unitType;
    private Integer bandMhzMin;
    private Integer bandMhzMax;
    private BigDecimal txPowerDbmMin;
    private BigDecimal txPowerDbmMax;
    private String serialNumber;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<RadioUnitLifecycleStage> lifecycleStage;
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
    public UUID getSiteId() { return siteId; }
    public void setSiteId(UUID siteId) { this.siteId = siteId; }
    public List<RadioUnitUnitType> getUnitType() { return unitType; }
    public void setUnitType(List<RadioUnitUnitType> unitType) { this.unitType = unitType; }
    public Integer getBandMhzMin() { return bandMhzMin; }
    public void setBandMhzMin(Integer bandMhzMin) { this.bandMhzMin = bandMhzMin; }
    public Integer getBandMhzMax() { return bandMhzMax; }
    public void setBandMhzMax(Integer bandMhzMax) { this.bandMhzMax = bandMhzMax; }
    public BigDecimal getTxPowerDbmMin() { return txPowerDbmMin; }
    public void setTxPowerDbmMin(BigDecimal txPowerDbmMin) { this.txPowerDbmMin = txPowerDbmMin; }
    public BigDecimal getTxPowerDbmMax() { return txPowerDbmMax; }
    public void setTxPowerDbmMax(BigDecimal txPowerDbmMax) { this.txPowerDbmMax = txPowerDbmMax; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
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
    public List<RadioUnitLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<RadioUnitLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
