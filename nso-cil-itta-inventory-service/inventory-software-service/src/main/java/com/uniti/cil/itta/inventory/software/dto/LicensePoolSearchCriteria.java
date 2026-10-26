package com.uniti.cil.itta.inventory.software.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the License Pool search endpoint. */
public class LicensePoolSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID vendorId;
    private String poolName;
    private Integer totalSeatsMin;
    private Integer totalSeatsMax;
    private Integer usedSeatsMin;
    private Integer usedSeatsMax;
    private LocalDate renewalDateFrom;
    private LocalDate renewalDateTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<LicensePoolLifecycleStage> lifecycleStage;
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
    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }
    public String getPoolName() { return poolName; }
    public void setPoolName(String poolName) { this.poolName = poolName; }
    public Integer getTotalSeatsMin() { return totalSeatsMin; }
    public void setTotalSeatsMin(Integer totalSeatsMin) { this.totalSeatsMin = totalSeatsMin; }
    public Integer getTotalSeatsMax() { return totalSeatsMax; }
    public void setTotalSeatsMax(Integer totalSeatsMax) { this.totalSeatsMax = totalSeatsMax; }
    public Integer getUsedSeatsMin() { return usedSeatsMin; }
    public void setUsedSeatsMin(Integer usedSeatsMin) { this.usedSeatsMin = usedSeatsMin; }
    public Integer getUsedSeatsMax() { return usedSeatsMax; }
    public void setUsedSeatsMax(Integer usedSeatsMax) { this.usedSeatsMax = usedSeatsMax; }
    public LocalDate getRenewalDateFrom() { return renewalDateFrom; }
    public void setRenewalDateFrom(LocalDate renewalDateFrom) { this.renewalDateFrom = renewalDateFrom; }
    public LocalDate getRenewalDateTo() { return renewalDateTo; }
    public void setRenewalDateTo(LocalDate renewalDateTo) { this.renewalDateTo = renewalDateTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<LicensePoolLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<LicensePoolLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
