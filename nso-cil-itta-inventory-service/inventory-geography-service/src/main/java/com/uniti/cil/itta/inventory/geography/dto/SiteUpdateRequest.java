package com.uniti.cil.itta.inventory.geography.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Site; null properties are left unchanged. */
public class SiteUpdateRequest {

    private Long expectedVersion;

    @Size(max = 255)
    private String code;
    @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    private UUID cityId;
    private SiteSiteType siteType;
    @Size(max = 255)
    private String address;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal latitude;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal longitude;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal powerKw;
    @Size(max = 255)
    private String accessHours;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
    private SiteLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    public Long getExpectedVersion() { return expectedVersion; }
    public void setExpectedVersion(Long expectedVersion) { this.expectedVersion = expectedVersion; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LifecycleStatus getStatus() { return status; }
    public void setStatus(LifecycleStatus status) { this.status = status; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public UUID getCityId() { return cityId; }
    public void setCityId(UUID cityId) { this.cityId = cityId; }
    public SiteSiteType getSiteType() { return siteType; }
    public void setSiteType(SiteSiteType siteType) { this.siteType = siteType; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    public BigDecimal getPowerKw() { return powerKw; }
    public void setPowerKw(BigDecimal powerKw) { this.powerKw = powerKw; }
    public String getAccessHours() { return accessHours; }
    public void setAccessHours(String accessHours) { this.accessHours = accessHours; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public SiteLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(SiteLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
