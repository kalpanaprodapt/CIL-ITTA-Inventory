package com.uniti.cil.itta.inventory.service.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Service Order search endpoint. */
public class ServiceOrderSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID customerId;
    private List<ServiceOrderOrderType> orderType;
    private LocalDate requestedOnFrom;
    private LocalDate requestedOnTo;
    private LocalDate targetDateFrom;
    private LocalDate targetDateTo;
    private Integer priorityMin;
    private Integer priorityMax;
    private String region;
    private String owner;
    private List<ServiceOrderCriticality> criticality;
    private String costCenter;

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
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public List<ServiceOrderOrderType> getOrderType() { return orderType; }
    public void setOrderType(List<ServiceOrderOrderType> orderType) { this.orderType = orderType; }
    public LocalDate getRequestedOnFrom() { return requestedOnFrom; }
    public void setRequestedOnFrom(LocalDate requestedOnFrom) { this.requestedOnFrom = requestedOnFrom; }
    public LocalDate getRequestedOnTo() { return requestedOnTo; }
    public void setRequestedOnTo(LocalDate requestedOnTo) { this.requestedOnTo = requestedOnTo; }
    public LocalDate getTargetDateFrom() { return targetDateFrom; }
    public void setTargetDateFrom(LocalDate targetDateFrom) { this.targetDateFrom = targetDateFrom; }
    public LocalDate getTargetDateTo() { return targetDateTo; }
    public void setTargetDateTo(LocalDate targetDateTo) { this.targetDateTo = targetDateTo; }
    public Integer getPriorityMin() { return priorityMin; }
    public void setPriorityMin(Integer priorityMin) { this.priorityMin = priorityMin; }
    public Integer getPriorityMax() { return priorityMax; }
    public void setPriorityMax(Integer priorityMax) { this.priorityMax = priorityMax; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<ServiceOrderCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<ServiceOrderCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
