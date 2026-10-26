package com.uniti.cil.itta.inventory.service.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Service Instance search endpoint. */
public class ServiceInstanceSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID catalogItemId;
    private UUID customerId;
    private LocalDate activatedOnFrom;
    private LocalDate activatedOnTo;
    private Long bandwidthMbpsMin;
    private Long bandwidthMbpsMax;
    private List<ServiceInstanceServiceState> serviceState;
    private List<ServiceInstanceLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<ServiceInstanceCriticality> criticality;

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
    public UUID getCatalogItemId() { return catalogItemId; }
    public void setCatalogItemId(UUID catalogItemId) { this.catalogItemId = catalogItemId; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public LocalDate getActivatedOnFrom() { return activatedOnFrom; }
    public void setActivatedOnFrom(LocalDate activatedOnFrom) { this.activatedOnFrom = activatedOnFrom; }
    public LocalDate getActivatedOnTo() { return activatedOnTo; }
    public void setActivatedOnTo(LocalDate activatedOnTo) { this.activatedOnTo = activatedOnTo; }
    public Long getBandwidthMbpsMin() { return bandwidthMbpsMin; }
    public void setBandwidthMbpsMin(Long bandwidthMbpsMin) { this.bandwidthMbpsMin = bandwidthMbpsMin; }
    public Long getBandwidthMbpsMax() { return bandwidthMbpsMax; }
    public void setBandwidthMbpsMax(Long bandwidthMbpsMax) { this.bandwidthMbpsMax = bandwidthMbpsMax; }
    public List<ServiceInstanceServiceState> getServiceState() { return serviceState; }
    public void setServiceState(List<ServiceInstanceServiceState> serviceState) { this.serviceState = serviceState; }
    public List<ServiceInstanceLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<ServiceInstanceLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<ServiceInstanceCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<ServiceInstanceCriticality> criticality) { this.criticality = criticality; }}
