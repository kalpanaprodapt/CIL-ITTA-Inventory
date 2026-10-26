package com.uniti.cil.itta.inventory.ipam.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Vrf Instance search endpoint. */
public class VrfInstanceSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String vrfName;
    private String routeDistinguisher;
    private String importTargets;
    private String exportTargets;
    private UUID deviceId;
    private String labels;
    private List<VrfInstanceLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;

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
    public List<VrfInstanceLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<VrfInstanceLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
