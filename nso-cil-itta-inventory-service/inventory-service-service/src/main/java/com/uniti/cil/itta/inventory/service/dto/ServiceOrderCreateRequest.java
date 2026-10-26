package com.uniti.cil.itta.inventory.service.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Service Order. */
public class ServiceOrderCreateRequest {

    @NotBlank @Size(max = 255)
    private String code;
    @NotBlank @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    @NotNull
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    @NotNull
    private UUID customerId;
    private ServiceOrderOrderType orderType;
    @NotNull
    private LocalDate requestedOn;
    private LocalDate targetDate;
    private Integer priority;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private ServiceOrderCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LifecycleStatus getStatus() { return status; }
    public void setStatus(LifecycleStatus status) { this.status = status; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public ServiceOrderOrderType getOrderType() { return orderType; }
    public void setOrderType(ServiceOrderOrderType orderType) { this.orderType = orderType; }
    public LocalDate getRequestedOn() { return requestedOn; }
    public void setRequestedOn(LocalDate requestedOn) { this.requestedOn = requestedOn; }
    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public ServiceOrderCriticality getCriticality() { return criticality; }
    public void setCriticality(ServiceOrderCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }}
