package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Antenna search endpoint. */
public class AntennaSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID sectorId;
    private String model;
    private BigDecimal heightMMin;
    private BigDecimal heightMMax;
    private BigDecimal azimuthDegMin;
    private BigDecimal azimuthDegMax;
    private BigDecimal tiltDegMin;
    private BigDecimal tiltDegMax;
    private BigDecimal gainDbiMin;
    private BigDecimal gainDbiMax;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<AntennaCriticality> criticality;
    private String costCenter;

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
    public UUID getSectorId() { return sectorId; }
    public void setSectorId(UUID sectorId) { this.sectorId = sectorId; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public BigDecimal getHeightMMin() { return heightMMin; }
    public void setHeightMMin(BigDecimal heightMMin) { this.heightMMin = heightMMin; }
    public BigDecimal getHeightMMax() { return heightMMax; }
    public void setHeightMMax(BigDecimal heightMMax) { this.heightMMax = heightMMax; }
    public BigDecimal getAzimuthDegMin() { return azimuthDegMin; }
    public void setAzimuthDegMin(BigDecimal azimuthDegMin) { this.azimuthDegMin = azimuthDegMin; }
    public BigDecimal getAzimuthDegMax() { return azimuthDegMax; }
    public void setAzimuthDegMax(BigDecimal azimuthDegMax) { this.azimuthDegMax = azimuthDegMax; }
    public BigDecimal getTiltDegMin() { return tiltDegMin; }
    public void setTiltDegMin(BigDecimal tiltDegMin) { this.tiltDegMin = tiltDegMin; }
    public BigDecimal getTiltDegMax() { return tiltDegMax; }
    public void setTiltDegMax(BigDecimal tiltDegMax) { this.tiltDegMax = tiltDegMax; }
    public BigDecimal getGainDbiMin() { return gainDbiMin; }
    public void setGainDbiMin(BigDecimal gainDbiMin) { this.gainDbiMin = gainDbiMin; }
    public BigDecimal getGainDbiMax() { return gainDbiMax; }
    public void setGainDbiMax(BigDecimal gainDbiMax) { this.gainDbiMax = gainDbiMax; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<AntennaCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<AntennaCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
