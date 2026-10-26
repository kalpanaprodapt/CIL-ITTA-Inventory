package com.uniti.cil.itta.inventory.service.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Service Endpoint search endpoint. */
public class ServiceEndpointSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID serviceId;
    private UUID siteId;
    private UUID portId;
    private List<ServiceEndpointEndpointRole> endpointRole;
    private String handoff;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<ServiceEndpointLifecycleStage> lifecycleStage;

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
    public UUID getServiceId() { return serviceId; }
    public void setServiceId(UUID serviceId) { this.serviceId = serviceId; }
    public UUID getSiteId() { return siteId; }
    public void setSiteId(UUID siteId) { this.siteId = siteId; }
    public UUID getPortId() { return portId; }
    public void setPortId(UUID portId) { this.portId = portId; }
    public List<ServiceEndpointEndpointRole> getEndpointRole() { return endpointRole; }
    public void setEndpointRole(List<ServiceEndpointEndpointRole> endpointRole) { this.endpointRole = endpointRole; }
    public String getHandoff() { return handoff; }
    public void setHandoff(String handoff) { this.handoff = handoff; }
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
    public List<ServiceEndpointLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<ServiceEndpointLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
