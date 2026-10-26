package com.uniti.cil.itta.inventory.optical.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Wdm System search endpoint. */
public class WdmSystemSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String systemName;
    private Integer channelCapacityMin;
    private Integer channelCapacityMax;
    private List<WdmSystemBand> band;
    private Integer spanCountMin;
    private Integer spanCountMax;
    private UUID vendorId;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<WdmSystemLifecycleStage> lifecycleStage;

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
    public String getSystemName() { return systemName; }
    public void setSystemName(String systemName) { this.systemName = systemName; }
    public Integer getChannelCapacityMin() { return channelCapacityMin; }
    public void setChannelCapacityMin(Integer channelCapacityMin) { this.channelCapacityMin = channelCapacityMin; }
    public Integer getChannelCapacityMax() { return channelCapacityMax; }
    public void setChannelCapacityMax(Integer channelCapacityMax) { this.channelCapacityMax = channelCapacityMax; }
    public List<WdmSystemBand> getBand() { return band; }
    public void setBand(List<WdmSystemBand> band) { this.band = band; }
    public Integer getSpanCountMin() { return spanCountMin; }
    public void setSpanCountMin(Integer spanCountMin) { this.spanCountMin = spanCountMin; }
    public Integer getSpanCountMax() { return spanCountMax; }
    public void setSpanCountMax(Integer spanCountMax) { this.spanCountMax = spanCountMax; }
    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }
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
    public List<WdmSystemLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<WdmSystemLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
