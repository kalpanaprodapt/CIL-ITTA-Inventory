package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Read model returned by the Antenna API. */
public class AntennaResponse {

    private UUID id;
    private String tenantId;
    private Long version;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    private String code;
    private String name;
    private String description;
    private LifecycleStatus status;
    private String externalRef;
    private UUID sectorId;
    private String model;
    private BigDecimal heightM;
    private BigDecimal azimuthDeg;
    private BigDecimal tiltDeg;
    private BigDecimal gainDbi;
    private String sourceSystem;
    private String region;
    private String owner;
    private AntennaCriticality criticality;
    private String costCenter;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

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
    public UUID getSectorId() { return sectorId; }
    public void setSectorId(UUID sectorId) { this.sectorId = sectorId; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public BigDecimal getHeightM() { return heightM; }
    public void setHeightM(BigDecimal heightM) { this.heightM = heightM; }
    public BigDecimal getAzimuthDeg() { return azimuthDeg; }
    public void setAzimuthDeg(BigDecimal azimuthDeg) { this.azimuthDeg = azimuthDeg; }
    public BigDecimal getTiltDeg() { return tiltDeg; }
    public void setTiltDeg(BigDecimal tiltDeg) { this.tiltDeg = tiltDeg; }
    public BigDecimal getGainDbi() { return gainDbi; }
    public void setGainDbi(BigDecimal gainDbi) { this.gainDbi = gainDbi; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public AntennaCriticality getCriticality() { return criticality; }
    public void setCriticality(AntennaCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
