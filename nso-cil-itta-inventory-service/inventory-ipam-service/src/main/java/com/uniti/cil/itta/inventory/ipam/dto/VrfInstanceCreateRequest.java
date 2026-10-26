package com.uniti.cil.itta.inventory.ipam.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Vrf Instance. */
public class VrfInstanceCreateRequest {

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
    @NotBlank @Size(max = 255)
    private String vrfName;
    @NotBlank @Size(max = 255)
    private String routeDistinguisher;
    @Size(max = 255)
    private String importTargets;
    @Size(max = 255)
    private String exportTargets;
    private UUID deviceId;
    @Size(max = 255)
    private String labels;
    private VrfInstanceLifecycleStage lifecycleStage;
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
    public String getVrfName() { return vrfName; }
    public void setVrfName(String vrfName) { this.vrfName = vrfName; }
    public String getRouteDistinguisher() { return routeDistinguisher; }
    public void setRouteDistinguisher(String routeDistinguisher) { this.routeDistinguisher = routeDistinguisher; }
    public String getImportTargets() { return importTargets; }
    public void setImportTargets(String importTargets) { this.importTargets = importTargets; }
    public String getExportTargets() { return exportTargets; }
    public void setExportTargets(String exportTargets) { this.exportTargets = exportTargets; }
    public UUID getDeviceId() { return deviceId; }
    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public VrfInstanceLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(VrfInstanceLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
