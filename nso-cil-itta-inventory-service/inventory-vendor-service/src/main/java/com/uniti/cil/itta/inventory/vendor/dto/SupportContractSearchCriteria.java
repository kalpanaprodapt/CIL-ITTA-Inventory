package com.uniti.cil.itta.inventory.vendor.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Support Contract search endpoint. */
public class SupportContractSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID vendorId;
    private String contractNumber;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate endDateFrom;
    private LocalDate endDateTo;
    private List<SupportContractCoverageLevel> coverageLevel;
    private BigDecimal annualCostMin;
    private BigDecimal annualCostMax;
    private String owner;
    private List<SupportContractCriticality> criticality;
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
    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }
    public String getContractNumber() { return contractNumber; }
    public void setContractNumber(String contractNumber) { this.contractNumber = contractNumber; }
    public LocalDate getStartDateFrom() { return startDateFrom; }
    public void setStartDateFrom(LocalDate startDateFrom) { this.startDateFrom = startDateFrom; }
    public LocalDate getStartDateTo() { return startDateTo; }
    public void setStartDateTo(LocalDate startDateTo) { this.startDateTo = startDateTo; }
    public LocalDate getEndDateFrom() { return endDateFrom; }
    public void setEndDateFrom(LocalDate endDateFrom) { this.endDateFrom = endDateFrom; }
    public LocalDate getEndDateTo() { return endDateTo; }
    public void setEndDateTo(LocalDate endDateTo) { this.endDateTo = endDateTo; }
    public List<SupportContractCoverageLevel> getCoverageLevel() { return coverageLevel; }
    public void setCoverageLevel(List<SupportContractCoverageLevel> coverageLevel) { this.coverageLevel = coverageLevel; }
    public BigDecimal getAnnualCostMin() { return annualCostMin; }
    public void setAnnualCostMin(BigDecimal annualCostMin) { this.annualCostMin = annualCostMin; }
    public BigDecimal getAnnualCostMax() { return annualCostMax; }
    public void setAnnualCostMax(BigDecimal annualCostMax) { this.annualCostMax = annualCostMax; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<SupportContractCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<SupportContractCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
