package com.uniti.cil.itta.inventory.optical.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Amplifier search endpoint. */
public class AmplifierSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID systemId;
    private List<AmplifierAmpType> ampType;
    private BigDecimal gainDbMin;
    private BigDecimal gainDbMax;
    private BigDecimal outputPowerDbmMin;
    private BigDecimal outputPowerDbmMax;
    private BigDecimal noiseFigureDbMin;
    private BigDecimal noiseFigureDbMax;
    private List<AmplifierLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<AmplifierCriticality> criticality;

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
    public UUID getSystemId() { return systemId; }
    public void setSystemId(UUID systemId) { this.systemId = systemId; }
    public List<AmplifierAmpType> getAmpType() { return ampType; }
    public void setAmpType(List<AmplifierAmpType> ampType) { this.ampType = ampType; }
    public BigDecimal getGainDbMin() { return gainDbMin; }
    public void setGainDbMin(BigDecimal gainDbMin) { this.gainDbMin = gainDbMin; }
    public BigDecimal getGainDbMax() { return gainDbMax; }
    public void setGainDbMax(BigDecimal gainDbMax) { this.gainDbMax = gainDbMax; }
    public BigDecimal getOutputPowerDbmMin() { return outputPowerDbmMin; }
    public void setOutputPowerDbmMin(BigDecimal outputPowerDbmMin) { this.outputPowerDbmMin = outputPowerDbmMin; }
    public BigDecimal getOutputPowerDbmMax() { return outputPowerDbmMax; }
    public void setOutputPowerDbmMax(BigDecimal outputPowerDbmMax) { this.outputPowerDbmMax = outputPowerDbmMax; }
    public BigDecimal getNoiseFigureDbMin() { return noiseFigureDbMin; }
    public void setNoiseFigureDbMin(BigDecimal noiseFigureDbMin) { this.noiseFigureDbMin = noiseFigureDbMin; }
    public BigDecimal getNoiseFigureDbMax() { return noiseFigureDbMax; }
    public void setNoiseFigureDbMax(BigDecimal noiseFigureDbMax) { this.noiseFigureDbMax = noiseFigureDbMax; }
    public List<AmplifierLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<AmplifierLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<AmplifierCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<AmplifierCriticality> criticality) { this.criticality = criticality; }}
