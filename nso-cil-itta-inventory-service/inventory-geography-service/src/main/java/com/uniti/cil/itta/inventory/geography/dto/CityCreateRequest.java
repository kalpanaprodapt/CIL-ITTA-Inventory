package com.uniti.cil.itta.inventory.geography.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a City. */
public class CityCreateRequest {

    @NotBlank @Size(max = 255)
    private String code;
    @NotBlank @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    @NotNull
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    @NotNull
    private UUID regionId;
    @Size(max = 255)
    private String postalPrefix;
    private Long population;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal latitude;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal longitude;
    @Size(max = 255)
    private String metroArea;
    @Size(max = 4000)
    private String notes;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
    private CityLifecycleStage lifecycleStage;
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
    public UUID getRegionId() { return regionId; }
    public void setRegionId(UUID regionId) { this.regionId = regionId; }
    public String getPostalPrefix() { return postalPrefix; }
    public void setPostalPrefix(String postalPrefix) { this.postalPrefix = postalPrefix; }
    public Long getPopulation() { return population; }
    public void setPopulation(Long population) { this.population = population; }
    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    public String getMetroArea() { return metroArea; }
    public void setMetroArea(String metroArea) { this.metroArea = metroArea; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public CityLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(CityLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
