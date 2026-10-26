package com.uniti.cil.itta.inventory.change.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Maintenance Window search endpoint. */
public class MaintenanceWindowSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String windowName;
    private Instant startsAtFrom;
    private Instant startsAtTo;
    private Instant endsAtFrom;
    private Instant endsAtTo;
    private String scope;
    private List<MaintenanceWindowImpactLevel> impactLevel;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<MaintenanceWindowLifecycleStage> lifecycleStage;
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
    public String getWindowName() { return windowName; }
    public void setWindowName(String windowName) { this.windowName = windowName; }
    public Instant getStartsAtFrom() { return startsAtFrom; }
    public void setStartsAtFrom(Instant startsAtFrom) { this.startsAtFrom = startsAtFrom; }
    public Instant getStartsAtTo() { return startsAtTo; }
    public void setStartsAtTo(Instant startsAtTo) { this.startsAtTo = startsAtTo; }
    public Instant getEndsAtFrom() { return endsAtFrom; }
    public void setEndsAtFrom(Instant endsAtFrom) { this.endsAtFrom = endsAtFrom; }
    public Instant getEndsAtTo() { return endsAtTo; }
    public void setEndsAtTo(Instant endsAtTo) { this.endsAtTo = endsAtTo; }
    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }
    public List<MaintenanceWindowImpactLevel> getImpactLevel() { return impactLevel; }
    public void setImpactLevel(List<MaintenanceWindowImpactLevel> impactLevel) { this.impactLevel = impactLevel; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<MaintenanceWindowLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<MaintenanceWindowLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
