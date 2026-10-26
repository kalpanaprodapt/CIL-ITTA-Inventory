package com.uniti.cil.itta.inventory.integration.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Sync Job search endpoint. */
public class SyncJobSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID endpointId;
    private List<SyncJobDirection> direction;
    private String scheduleCron;
    private Instant lastRunAtFrom;
    private Instant lastRunAtTo;
    private List<SyncJobLastStatus> lastStatus;
    private String owner;
    private List<SyncJobCriticality> criticality;
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
    public UUID getEndpointId() { return endpointId; }
    public void setEndpointId(UUID endpointId) { this.endpointId = endpointId; }
    public List<SyncJobDirection> getDirection() { return direction; }
    public void setDirection(List<SyncJobDirection> direction) { this.direction = direction; }
    public String getScheduleCron() { return scheduleCron; }
    public void setScheduleCron(String scheduleCron) { this.scheduleCron = scheduleCron; }
    public Instant getLastRunAtFrom() { return lastRunAtFrom; }
    public void setLastRunAtFrom(Instant lastRunAtFrom) { this.lastRunAtFrom = lastRunAtFrom; }
    public Instant getLastRunAtTo() { return lastRunAtTo; }
    public void setLastRunAtTo(Instant lastRunAtTo) { this.lastRunAtTo = lastRunAtTo; }
    public List<SyncJobLastStatus> getLastStatus() { return lastStatus; }
    public void setLastStatus(List<SyncJobLastStatus> lastStatus) { this.lastStatus = lastStatus; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<SyncJobCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<SyncJobCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
