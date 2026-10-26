package com.uniti.cil.itta.inventory.geography.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the City search endpoint. */
public class CitySearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID regionId;
    private String postalPrefix;
    private Long populationMin;
    private Long populationMax;
    private BigDecimal latitudeMin;
    private BigDecimal latitudeMax;
    private BigDecimal longitudeMin;
    private BigDecimal longitudeMax;
    private String metroArea;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<CityLifecycleStage> lifecycleStage;

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
    public UUID getRegionId() { return regionId; }
    public void setRegionId(UUID regionId) { this.regionId = regionId; }
    public String getPostalPrefix() { return postalPrefix; }
    public void setPostalPrefix(String postalPrefix) { this.postalPrefix = postalPrefix; }
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
    public String getMetroArea() { return metroArea; }
    public void setMetroArea(String metroArea) { this.metroArea = metroArea; }
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
    public List<CityLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<CityLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
