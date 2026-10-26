package com.uniti.cil.itta.inventory.customer.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Contract search endpoint. */
public class ContractSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID customerId;
    private String contractNumber;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate endDateFrom;
    private LocalDate endDateTo;
    private Integer termMonthsMin;
    private Integer termMonthsMax;
    private Boolean autoRenew;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<ContractCriticality> criticality;
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
    public Integer getTermMonthsMin() { return termMonthsMin; }
    public void setTermMonthsMin(Integer termMonthsMin) { this.termMonthsMin = termMonthsMin; }
    public Integer getTermMonthsMax() { return termMonthsMax; }
    public void setTermMonthsMax(Integer termMonthsMax) { this.termMonthsMax = termMonthsMax; }
    public Boolean getAutoRenew() { return autoRenew; }
    public void setAutoRenew(Boolean autoRenew) { this.autoRenew = autoRenew; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<ContractCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<ContractCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
