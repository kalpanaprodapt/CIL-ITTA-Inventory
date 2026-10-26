package com.uniti.cil.itta.inventory.port.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Transceiver search endpoint. */
public class TransceiverSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID portId;
    private List<TransceiverFormFactor> formFactor;
    private Integer wavelengthNmMin;
    private Integer wavelengthNmMax;
    private BigDecimal reachKmMin;
    private BigDecimal reachKmMax;
    private String serialNumber;
    private String vendorPart;
    private String labels;
    private List<TransceiverLifecycleStage> lifecycleStage;
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
    public UUID getPortId() { return portId; }
    public void setPortId(UUID portId) { this.portId = portId; }
    public List<TransceiverFormFactor> getFormFactor() { return formFactor; }
    public void setFormFactor(List<TransceiverFormFactor> formFactor) { this.formFactor = formFactor; }
    public Integer getWavelengthNmMin() { return wavelengthNmMin; }
    public void setWavelengthNmMin(Integer wavelengthNmMin) { this.wavelengthNmMin = wavelengthNmMin; }
    public Integer getWavelengthNmMax() { return wavelengthNmMax; }
    public void setWavelengthNmMax(Integer wavelengthNmMax) { this.wavelengthNmMax = wavelengthNmMax; }
    public BigDecimal getReachKmMin() { return reachKmMin; }
    public void setReachKmMin(BigDecimal reachKmMin) { this.reachKmMin = reachKmMin; }
    public BigDecimal getReachKmMax() { return reachKmMax; }
    public void setReachKmMax(BigDecimal reachKmMax) { this.reachKmMax = reachKmMax; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public String getVendorPart() { return vendorPart; }
    public void setVendorPart(String vendorPart) { this.vendorPart = vendorPart; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<TransceiverLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<TransceiverLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
