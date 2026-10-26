package com.uniti.cil.itta.inventory.topology.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Topology View search endpoint. */
public class TopologyViewSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String viewName;
    private List<TopologyViewViewType> viewType;
    private String scopeFilter;
    private Instant refreshedAtFrom;
    private Instant refreshedAtTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<TopologyViewLifecycleStage> lifecycleStage;
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
    public String getViewName() { return viewName; }
    public void setViewName(String viewName) { this.viewName = viewName; }
    public List<TopologyViewViewType> getViewType() { return viewType; }
    public void setViewType(List<TopologyViewViewType> viewType) { this.viewType = viewType; }
    public String getScopeFilter() { return scopeFilter; }
    public void setScopeFilter(String scopeFilter) { this.scopeFilter = scopeFilter; }
    public Instant getRefreshedAtFrom() { return refreshedAtFrom; }
    public void setRefreshedAtFrom(Instant refreshedAtFrom) { this.refreshedAtFrom = refreshedAtFrom; }
    public Instant getRefreshedAtTo() { return refreshedAtTo; }
    public void setRefreshedAtTo(Instant refreshedAtTo) { this.refreshedAtTo = refreshedAtTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<TopologyViewLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<TopologyViewLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
