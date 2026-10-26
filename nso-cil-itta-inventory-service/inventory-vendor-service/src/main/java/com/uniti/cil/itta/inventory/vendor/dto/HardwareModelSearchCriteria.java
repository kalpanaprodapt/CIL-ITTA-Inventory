package com.uniti.cil.itta.inventory.vendor.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Hardware Model search endpoint. */
public class HardwareModelSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID vendorId;
    private String modelNumber;
    private String formFactor;
    private Integer powerWattsMin;
    private Integer powerWattsMax;
    private LocalDate eolDateFrom;
    private LocalDate eolDateTo;
    private LocalDate eosDateFrom;
    private LocalDate eosDateTo;
    private String labels;
    private List<HardwareModelLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;

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
    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }
    public String getModelNumber() { return modelNumber; }
    public void setModelNumber(String modelNumber) { this.modelNumber = modelNumber; }
    public String getFormFactor() { return formFactor; }
    public void setFormFactor(String formFactor) { this.formFactor = formFactor; }
    public Integer getPowerWattsMin() { return powerWattsMin; }
    public void setPowerWattsMin(Integer powerWattsMin) { this.powerWattsMin = powerWattsMin; }
    public Integer getPowerWattsMax() { return powerWattsMax; }
    public void setPowerWattsMax(Integer powerWattsMax) { this.powerWattsMax = powerWattsMax; }
    public LocalDate getEolDateFrom() { return eolDateFrom; }
    public void setEolDateFrom(LocalDate eolDateFrom) { this.eolDateFrom = eolDateFrom; }
    public LocalDate getEolDateTo() { return eolDateTo; }
    public void setEolDateTo(LocalDate eolDateTo) { this.eolDateTo = eolDateTo; }
    public LocalDate getEosDateFrom() { return eosDateFrom; }
    public void setEosDateFrom(LocalDate eosDateFrom) { this.eosDateFrom = eosDateFrom; }
    public LocalDate getEosDateTo() { return eosDateTo; }
    public void setEosDateTo(LocalDate eosDateTo) { this.eosDateTo = eosDateTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<HardwareModelLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<HardwareModelLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
