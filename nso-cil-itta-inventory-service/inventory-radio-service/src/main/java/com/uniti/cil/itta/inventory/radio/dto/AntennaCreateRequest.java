package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Antenna. */
public class AntennaCreateRequest {

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
    private UUID sectorId;
    @Size(max = 255)
    private String model;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal heightM;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal azimuthDeg;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal tiltDeg;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal gainDbi;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private AntennaCriticality criticality;
    @Size(max = 255)
    private String costCenter;
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
