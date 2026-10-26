package com.uniti.cil.itta.inventory.optical.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Optical Channel search endpoint. */
public class OpticalChannelSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private BigDecimal wavelengthNmMin;
    private BigDecimal wavelengthNmMax;
    private Integer channelNumberMin;
    private Integer channelNumberMax;
    private List<OpticalChannelGridType> gridType;
    private BigDecimal bitrateGbpsMin;
    private BigDecimal bitrateGbpsMax;
    private UUID systemId;
    private List<OpticalChannelCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;

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
    public BigDecimal getWavelengthNmMin() { return wavelengthNmMin; }
    public void setWavelengthNmMin(BigDecimal wavelengthNmMin) { this.wavelengthNmMin = wavelengthNmMin; }
    public BigDecimal getWavelengthNmMax() { return wavelengthNmMax; }
    public void setWavelengthNmMax(BigDecimal wavelengthNmMax) { this.wavelengthNmMax = wavelengthNmMax; }
    public Integer getChannelNumberMin() { return channelNumberMin; }
    public void setChannelNumberMin(Integer channelNumberMin) { this.channelNumberMin = channelNumberMin; }
    public Integer getChannelNumberMax() { return channelNumberMax; }
    public void setChannelNumberMax(Integer channelNumberMax) { this.channelNumberMax = channelNumberMax; }
    public List<OpticalChannelGridType> getGridType() { return gridType; }
    public void setGridType(List<OpticalChannelGridType> gridType) { this.gridType = gridType; }
    public BigDecimal getBitrateGbpsMin() { return bitrateGbpsMin; }
    public void setBitrateGbpsMin(BigDecimal bitrateGbpsMin) { this.bitrateGbpsMin = bitrateGbpsMin; }
    public BigDecimal getBitrateGbpsMax() { return bitrateGbpsMax; }
    public void setBitrateGbpsMax(BigDecimal bitrateGbpsMax) { this.bitrateGbpsMax = bitrateGbpsMax; }
    public UUID getSystemId() { return systemId; }
    public void setSystemId(UUID systemId) { this.systemId = systemId; }
    public List<OpticalChannelCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<OpticalChannelCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }}
