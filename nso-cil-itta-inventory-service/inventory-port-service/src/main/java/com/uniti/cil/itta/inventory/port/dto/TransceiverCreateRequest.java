package com.uniti.cil.itta.inventory.port.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Transceiver. */
public class TransceiverCreateRequest {

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
    private UUID portId;
    private TransceiverFormFactor formFactor;
    private Integer wavelengthNm;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal reachKm;
    @Size(max = 255)
    private String serialNumber;
    @Size(max = 255)
    private String vendorPart;
    @Size(max = 255)
    private String labels;
    private TransceiverLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
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
    public UUID getPortId() { return portId; }
    public void setPortId(UUID portId) { this.portId = portId; }
    public TransceiverFormFactor getFormFactor() { return formFactor; }
    public void setFormFactor(TransceiverFormFactor formFactor) { this.formFactor = formFactor; }
    public Integer getWavelengthNm() { return wavelengthNm; }
    public void setWavelengthNm(Integer wavelengthNm) { this.wavelengthNm = wavelengthNm; }
    public BigDecimal getReachKm() { return reachKm; }
    public void setReachKm(BigDecimal reachKm) { this.reachKm = reachKm; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public String getVendorPart() { return vendorPart; }
    public void setVendorPart(String vendorPart) { this.vendorPart = vendorPart; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public TransceiverLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(TransceiverLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
