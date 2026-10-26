package com.uniti.cil.itta.inventory.geography.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Site search endpoint. */
public class SiteSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID cityId;
    private List<SiteSiteType> siteType;
    private String address;
    private BigDecimal latitudeMin;
    private BigDecimal latitudeMax;
    private BigDecimal longitudeMin;
    private BigDecimal longitudeMax;
    private BigDecimal powerKwMin;
    private BigDecimal powerKwMax;
    private String accessHours;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<SiteLifecycleStage> lifecycleStage;
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
    public UUID getCityId() { return cityId; }
    public void setCityId(UUID cityId) { this.cityId = cityId; }
    public List<SiteSiteType> getSiteType() { return siteType; }
    public void setSiteType(List<SiteSiteType> siteType) { this.siteType = siteType; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BigDecimal getLatitudeMin() { return latitudeMin; }
    public void setLatitudeMin(BigDecimal latitudeMin) { this.latitudeMin = latitudeMin; }
    public BigDecimal getLatitudeMax() { return latitudeMax; }
    public void setLatitudeMax(BigDecimal latitudeMax) { this.latitudeMax = latitudeMax; }
    public BigDecimal getLongitudeMin() { return longitudeMin; }
    public void setLongitudeMin(BigDecimal longitudeMin) { this.longitudeMin = longitudeMin; }
    public BigDecimal getLongitudeMax() { return longitudeMax; }
    public void setLongitudeMax(BigDecimal longitudeMax) { this.longitudeMax = longitudeMax; }
    public BigDecimal getPowerKwMin() { return powerKwMin; }
    public void setPowerKwMin(BigDecimal powerKwMin) { this.powerKwMin = powerKwMin; }
    public BigDecimal getPowerKwMax() { return powerKwMax; }
    public void setPowerKwMax(BigDecimal powerKwMax) { this.powerKwMax = powerKwMax; }
    public String getAccessHours() { return accessHours; }
    public void setAccessHours(String accessHours) { this.accessHours = accessHours; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<SiteLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<SiteLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
