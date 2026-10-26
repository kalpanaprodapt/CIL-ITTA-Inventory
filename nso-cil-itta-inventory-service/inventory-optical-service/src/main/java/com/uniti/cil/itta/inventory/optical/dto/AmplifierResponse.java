package com.uniti.cil.itta.inventory.optical.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Read model returned by the Amplifier API. */
public class AmplifierResponse {

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
    private UUID systemId;
    private AmplifierAmpType ampType;
    private BigDecimal gainDb;
    private BigDecimal outputPowerDbm;
    private BigDecimal noiseFigureDb;
    private AmplifierLifecycleStage lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private AmplifierCriticality criticality;

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
