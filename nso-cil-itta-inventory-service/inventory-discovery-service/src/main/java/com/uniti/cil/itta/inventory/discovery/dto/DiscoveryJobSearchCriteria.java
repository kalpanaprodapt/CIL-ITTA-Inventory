package com.uniti.cil.itta.inventory.discovery.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Discovery Job search endpoint. */
public class DiscoveryJobSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID profileId;
    private Instant startedAtFrom;
    private Instant startedAtTo;
    private Instant finishedAtFrom;
    private Instant finishedAtTo;
    private List<DiscoveryJobJobState> jobState;
    private Integer assetsFoundMin;
    private Integer assetsFoundMax;
    private String owner;
    private List<DiscoveryJobCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;

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
    public UUID getProfileId() { return profileId; }
    public void setProfileId(UUID profileId) { this.profileId = profileId; }
    public Instant getStartedAtFrom() { return startedAtFrom; }
    public void setStartedAtFrom(Instant startedAtFrom) { this.startedAtFrom = startedAtFrom; }
    public Instant getStartedAtTo() { return startedAtTo; }
    public void setStartedAtTo(Instant startedAtTo) { this.startedAtTo = startedAtTo; }
    public Instant getFinishedAtFrom() { return finishedAtFrom; }
    public void setFinishedAtFrom(Instant finishedAtFrom) { this.finishedAtFrom = finishedAtFrom; }
    public Instant getFinishedAtTo() { return finishedAtTo; }
    public void setFinishedAtTo(Instant finishedAtTo) { this.finishedAtTo = finishedAtTo; }
    public List<DiscoveryJobJobState> getJobState() { return jobState; }
    public void setJobState(List<DiscoveryJobJobState> jobState) { this.jobState = jobState; }
    public Integer getAssetsFoundMin() { return assetsFoundMin; }
    public void setAssetsFoundMin(Integer assetsFoundMin) { this.assetsFoundMin = assetsFoundMin; }
    public Integer getAssetsFoundMax() { return assetsFoundMax; }
    public void setAssetsFoundMax(Integer assetsFoundMax) { this.assetsFoundMax = assetsFoundMax; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<DiscoveryJobCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<DiscoveryJobCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
