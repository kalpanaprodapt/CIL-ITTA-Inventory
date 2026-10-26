package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Sector search endpoint. */
public class SectorSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID baseStationId;
    private Integer sectorNumberMin;
    private Integer sectorNumberMax;
    private Integer pciMin;
    private Integer pciMax;
    private Integer frequencyMhzMin;
    private Integer frequencyMhzMax;
    private Integer bandwidthMhzMin;
    private Integer bandwidthMhzMax;
    private String owner;
    private List<SectorCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;

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
    public UUID getBaseStationId() { return baseStationId; }
    public void setBaseStationId(UUID baseStationId) { this.baseStationId = baseStationId; }
    public Integer getSectorNumberMin() { return sectorNumberMin; }
    public void setSectorNumberMin(Integer sectorNumberMin) { this.sectorNumberMin = sectorNumberMin; }
    public Integer getSectorNumberMax() { return sectorNumberMax; }
    public void setSectorNumberMax(Integer sectorNumberMax) { this.sectorNumberMax = sectorNumberMax; }
    public Integer getPciMin() { return pciMin; }
    public void setPciMin(Integer pciMin) { this.pciMin = pciMin; }
    public Integer getPciMax() { return pciMax; }
    public void setPciMax(Integer pciMax) { this.pciMax = pciMax; }
    public Integer getFrequencyMhzMin() { return frequencyMhzMin; }
    public void setFrequencyMhzMin(Integer frequencyMhzMin) { this.frequencyMhzMin = frequencyMhzMin; }
    public Integer getFrequencyMhzMax() { return frequencyMhzMax; }
    public void setFrequencyMhzMax(Integer frequencyMhzMax) { this.frequencyMhzMax = frequencyMhzMax; }
    public Integer getBandwidthMhzMin() { return bandwidthMhzMin; }
    public void setBandwidthMhzMin(Integer bandwidthMhzMin) { this.bandwidthMhzMin = bandwidthMhzMin; }
    public Integer getBandwidthMhzMax() { return bandwidthMhzMax; }
    public void setBandwidthMhzMax(Integer bandwidthMhzMax) { this.bandwidthMhzMax = bandwidthMhzMax; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<SectorCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<SectorCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
