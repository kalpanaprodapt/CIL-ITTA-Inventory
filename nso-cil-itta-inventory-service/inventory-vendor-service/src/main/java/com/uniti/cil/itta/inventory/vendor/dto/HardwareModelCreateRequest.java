package com.uniti.cil.itta.inventory.vendor.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Hardware Model. */
public class HardwareModelCreateRequest {

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
    private UUID vendorId;
    @NotBlank @Size(max = 255)
    private String modelNumber;
    @Size(max = 255)
    private String formFactor;
    private Integer powerWatts;
    private LocalDate eolDate;
    private LocalDate eosDate;
    @Size(max = 255)
    private String labels;
    private HardwareModelLifecycleStage lifecycleStage;
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
    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }
    public String getModelNumber() { return modelNumber; }
    public void setModelNumber(String modelNumber) { this.modelNumber = modelNumber; }
    public String getFormFactor() { return formFactor; }
    public void setFormFactor(String formFactor) { this.formFactor = formFactor; }
    public Integer getPowerWatts() { return powerWatts; }
    public void setPowerWatts(Integer powerWatts) { this.powerWatts = powerWatts; }
    public LocalDate getEolDate() { return eolDate; }
    public void setEolDate(LocalDate eolDate) { this.eolDate = eolDate; }
    public LocalDate getEosDate() { return eosDate; }
    public void setEosDate(LocalDate eosDate) { this.eosDate = eosDate; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public HardwareModelLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(HardwareModelLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
