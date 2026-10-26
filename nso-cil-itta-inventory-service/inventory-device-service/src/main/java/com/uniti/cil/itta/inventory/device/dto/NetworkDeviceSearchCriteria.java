package com.uniti.cil.itta.inventory.device.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Network Device search endpoint. */
public class NetworkDeviceSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String hostname;
    private List<NetworkDeviceDeviceRole> deviceRole;
    private String mgmtIp;
    private String serialNumber;
    private UUID modelId;
    private UUID rackUnitId;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<NetworkDeviceLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;

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
    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    public List<NetworkDeviceDeviceRole> getDeviceRole() { return deviceRole; }
    public void setDeviceRole(List<NetworkDeviceDeviceRole> deviceRole) { this.deviceRole = deviceRole; }
    public String getMgmtIp() { return mgmtIp; }
    public void setMgmtIp(String mgmtIp) { this.mgmtIp = mgmtIp; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public UUID getModelId() { return modelId; }
    public void setModelId(UUID modelId) { this.modelId = modelId; }
    public UUID getRackUnitId() { return rackUnitId; }
    public void setRackUnitId(UUID rackUnitId) { this.rackUnitId = rackUnitId; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<NetworkDeviceLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<NetworkDeviceLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
