package com.uniti.cil.itta.inventory.cabling.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Patch Port; null properties are left unchanged. */
public class PatchPortUpdateRequest {

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
    private UUID panelId;
    private Integer portNumber;
    private UUID connectedCableId;
    @Size(max = 255)
    private String polarity;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
    private PatchPortLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
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
    public UUID getPanelId() { return panelId; }
    public void setPanelId(UUID panelId) { this.panelId = panelId; }
    public Integer getPortNumber() { return portNumber; }
    public void setPortNumber(Integer portNumber) { this.portNumber = portNumber; }
    public UUID getConnectedCableId() { return connectedCableId; }
    public void setConnectedCableId(UUID connectedCableId) { this.connectedCableId = connectedCableId; }
    public String getPolarity() { return polarity; }
    public void setPolarity(String polarity) { this.polarity = polarity; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public PatchPortLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(PatchPortLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
