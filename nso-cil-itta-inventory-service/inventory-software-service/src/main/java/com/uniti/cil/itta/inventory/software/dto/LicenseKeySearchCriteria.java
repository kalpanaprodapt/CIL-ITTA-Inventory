package com.uniti.cil.itta.inventory.software.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the License Key search endpoint. */
public class LicenseKeySearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID poolId;
    private String keyValue;
    private String feature;
    private LocalDate expiresOnFrom;
    private LocalDate expiresOnTo;
    private UUID assignedDeviceId;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<LicenseKeyLifecycleStage> lifecycleStage;

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
    public UUID getPoolId() { return poolId; }
    public void setPoolId(UUID poolId) { this.poolId = poolId; }
    public String getKeyValue() { return keyValue; }
    public void setKeyValue(String keyValue) { this.keyValue = keyValue; }
    public String getFeature() { return feature; }
    public void setFeature(String feature) { this.feature = feature; }
    public LocalDate getExpiresOnFrom() { return expiresOnFrom; }
    public void setExpiresOnFrom(LocalDate expiresOnFrom) { this.expiresOnFrom = expiresOnFrom; }
    public LocalDate getExpiresOnTo() { return expiresOnTo; }
    public void setExpiresOnTo(LocalDate expiresOnTo) { this.expiresOnTo = expiresOnTo; }
    public UUID getAssignedDeviceId() { return assignedDeviceId; }
    public void setAssignedDeviceId(UUID assignedDeviceId) { this.assignedDeviceId = assignedDeviceId; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<LicenseKeyLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<LicenseKeyLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
