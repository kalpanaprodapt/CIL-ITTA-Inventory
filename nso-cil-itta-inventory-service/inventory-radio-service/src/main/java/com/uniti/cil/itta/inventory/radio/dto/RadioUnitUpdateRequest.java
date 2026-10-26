package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Radio Unit; null properties are left unchanged. */
public class RadioUnitUpdateRequest {

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
    private UUID siteId;
    private RadioUnitUnitType unitType;
    private Integer bandMhz;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal txPowerDbm;
    @Size(max = 255)
    private String serialNumber;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
    private RadioUnitLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
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
    public UUID getSiteId() { return siteId; }
    public void setSiteId(UUID siteId) { this.siteId = siteId; }
    public RadioUnitUnitType getUnitType() { return unitType; }
    public void setUnitType(RadioUnitUnitType unitType) { this.unitType = unitType; }
    public Integer getBandMhz() { return bandMhz; }
    public void setBandMhz(Integer bandMhz) { this.bandMhz = bandMhz; }
    public BigDecimal getTxPowerDbm() { return txPowerDbm; }
    public void setTxPowerDbm(BigDecimal txPowerDbm) { this.txPowerDbm = txPowerDbm; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public RadioUnitLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(RadioUnitLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
