package com.uniti.cil.itta.inventory.port.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Lag Group search endpoint. */
public class LagGroupSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID deviceId;
    private String lagName;
    private Integer memberCountMin;
    private Integer memberCountMax;
    private List<LagGroupLacpMode> lacpMode;
    private Integer minLinksMin;
    private Integer minLinksMax;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<LagGroupLifecycleStage> lifecycleStage;
    private String sourceSystem;

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
    public UUID getDeviceId() { return deviceId; }
    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }
    public String getLagName() { return lagName; }
    public void setLagName(String lagName) { this.lagName = lagName; }
    public Integer getMemberCountMin() { return memberCountMin; }
    public void setMemberCountMin(Integer memberCountMin) { this.memberCountMin = memberCountMin; }
    public Integer getMemberCountMax() { return memberCountMax; }
    public void setMemberCountMax(Integer memberCountMax) { this.memberCountMax = memberCountMax; }
    public List<LagGroupLacpMode> getLacpMode() { return lacpMode; }
    public void setLacpMode(List<LagGroupLacpMode> lacpMode) { this.lacpMode = lacpMode; }
    public Integer getMinLinksMin() { return minLinksMin; }
    public void setMinLinksMin(Integer minLinksMin) { this.minLinksMin = minLinksMin; }
    public Integer getMinLinksMax() { return minLinksMax; }
    public void setMinLinksMax(Integer minLinksMax) { this.minLinksMax = minLinksMax; }
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
    public List<LagGroupLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<LagGroupLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
