package com.uniti.cil.itta.inventory.geography.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Region search endpoint. */
public class RegionSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID countryId;
    private List<RegionRegionType> regionType;
    private Long populationMin;
    private Long populationMax;
    private BigDecimal latitudeMin;
    private BigDecimal latitudeMax;
    private BigDecimal longitudeMin;
    private BigDecimal longitudeMax;
    private List<RegionCriticality> criticality;
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
    public UUID getCountryId() { return countryId; }
    public void setCountryId(UUID countryId) { this.countryId = countryId; }
    public List<RegionRegionType> getRegionType() { return regionType; }
    public void setRegionType(List<RegionRegionType> regionType) { this.regionType = regionType; }
    public Long getPopulationMin() { return populationMin; }
    public void setPopulationMin(Long populationMin) { this.populationMin = populationMin; }
    public Long getPopulationMax() { return populationMax; }
    public void setPopulationMax(Long populationMax) { this.populationMax = populationMax; }
    public BigDecimal getLatitudeMin() { return latitudeMin; }
    public void setLatitudeMin(BigDecimal latitudeMin) { this.latitudeMin = latitudeMin; }
    public BigDecimal getLatitudeMax() { return latitudeMax; }
    public void setLatitudeMax(BigDecimal latitudeMax) { this.latitudeMax = latitudeMax; }
    public BigDecimal getLongitudeMin() { return longitudeMin; }
    public void setLongitudeMin(BigDecimal longitudeMin) { this.longitudeMin = longitudeMin; }
    public BigDecimal getLongitudeMax() { return longitudeMax; }
    public void setLongitudeMax(BigDecimal longitudeMax) { this.longitudeMax = longitudeMax; }
    public List<RegionCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<RegionCriticality> criticality) { this.criticality = criticality; }
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
