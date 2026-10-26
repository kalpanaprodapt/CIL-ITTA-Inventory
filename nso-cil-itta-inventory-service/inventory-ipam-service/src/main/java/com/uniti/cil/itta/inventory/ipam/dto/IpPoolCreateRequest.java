package com.uniti.cil.itta.inventory.ipam.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Ip Pool. */
public class IpPoolCreateRequest {

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
    private UUID subnetId;
    @NotBlank @Size(max = 255)
    private String rangeStart;
    @NotBlank @Size(max = 255)
    private String rangeEnd;
    private IpPoolPoolType poolType;
    private Integer leaseSeconds;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
    private IpPoolLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
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
    public UUID getSubnetId() { return subnetId; }
    public void setSubnetId(UUID subnetId) { this.subnetId = subnetId; }
    public String getRangeStart() { return rangeStart; }
    public void setRangeStart(String rangeStart) { this.rangeStart = rangeStart; }
    public String getRangeEnd() { return rangeEnd; }
    public void setRangeEnd(String rangeEnd) { this.rangeEnd = rangeEnd; }
    public IpPoolPoolType getPoolType() { return poolType; }
    public void setPoolType(IpPoolPoolType poolType) { this.poolType = poolType; }
    public Integer getLeaseSeconds() { return leaseSeconds; }
    public void setLeaseSeconds(Integer leaseSeconds) { this.leaseSeconds = leaseSeconds; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public IpPoolLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(IpPoolLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
