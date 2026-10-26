package com.uniti.cil.itta.inventory.optical.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Optical Channel. */
public class OpticalChannelCreateRequest {

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
    @NotNull @Digits(integer = 14, fraction = 4)
    private BigDecimal wavelengthNm;
    @NotNull
    private Integer channelNumber;
    private OpticalChannelGridType gridType;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal bitrateGbps;
    private UUID systemId;
    private OpticalChannelCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
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
    public BigDecimal getWavelengthNm() { return wavelengthNm; }
    public void setWavelengthNm(BigDecimal wavelengthNm) { this.wavelengthNm = wavelengthNm; }
    public Integer getChannelNumber() { return channelNumber; }
    public void setChannelNumber(Integer channelNumber) { this.channelNumber = channelNumber; }
    public OpticalChannelGridType getGridType() { return gridType; }
    public void setGridType(OpticalChannelGridType gridType) { this.gridType = gridType; }
    public BigDecimal getBitrateGbps() { return bitrateGbps; }
    public void setBitrateGbps(BigDecimal bitrateGbps) { this.bitrateGbps = bitrateGbps; }
    public UUID getSystemId() { return systemId; }
    public void setSystemId(UUID systemId) { this.systemId = systemId; }
    public OpticalChannelCriticality getCriticality() { return criticality; }
    public void setCriticality(OpticalChannelCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }}
