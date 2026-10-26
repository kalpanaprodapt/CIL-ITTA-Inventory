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

/** Payload used to create a Amplifier. */
public class AmplifierCreateRequest {

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
    private UUID systemId;
    private AmplifierAmpType ampType;
    @NotNull @Digits(integer = 14, fraction = 4)
    private BigDecimal gainDb;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal outputPowerDbm;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal noiseFigureDb;
    private AmplifierLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private AmplifierCriticality criticality;
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
    public UUID getSystemId() { return systemId; }
    public void setSystemId(UUID systemId) { this.systemId = systemId; }
    public AmplifierAmpType getAmpType() { return ampType; }
    public void setAmpType(AmplifierAmpType ampType) { this.ampType = ampType; }
    public BigDecimal getGainDb() { return gainDb; }
    public void setGainDb(BigDecimal gainDb) { this.gainDb = gainDb; }
    public BigDecimal getOutputPowerDbm() { return outputPowerDbm; }
    public void setOutputPowerDbm(BigDecimal outputPowerDbm) { this.outputPowerDbm = outputPowerDbm; }
    public BigDecimal getNoiseFigureDb() { return noiseFigureDb; }
    public void setNoiseFigureDb(BigDecimal noiseFigureDb) { this.noiseFigureDb = noiseFigureDb; }
    public AmplifierLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(AmplifierLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public AmplifierCriticality getCriticality() { return criticality; }
    public void setCriticality(AmplifierCriticality criticality) { this.criticality = criticality; }}
