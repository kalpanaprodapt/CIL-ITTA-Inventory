package com.uniti.cil.itta.inventory.assetmgmt.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Purchase Order search endpoint. */
public class PurchaseOrderSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String poNumber;
    private UUID vendorId;
    private LocalDate orderedOnFrom;
    private LocalDate orderedOnTo;
    private BigDecimal totalAmountMin;
    private BigDecimal totalAmountMax;
    private String currency;
    private String owner;
    private List<PurchaseOrderCriticality> criticality;
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
    public String getPoNumber() { return poNumber; }
    public void setPoNumber(String poNumber) { this.poNumber = poNumber; }
    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }
    public LocalDate getOrderedOnFrom() { return orderedOnFrom; }
    public void setOrderedOnFrom(LocalDate orderedOnFrom) { this.orderedOnFrom = orderedOnFrom; }
    public LocalDate getOrderedOnTo() { return orderedOnTo; }
    public void setOrderedOnTo(LocalDate orderedOnTo) { this.orderedOnTo = orderedOnTo; }
    public BigDecimal getTotalAmountMin() { return totalAmountMin; }
    public void setTotalAmountMin(BigDecimal totalAmountMin) { this.totalAmountMin = totalAmountMin; }
    public BigDecimal getTotalAmountMax() { return totalAmountMax; }
    public void setTotalAmountMax(BigDecimal totalAmountMax) { this.totalAmountMax = totalAmountMax; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<PurchaseOrderCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<PurchaseOrderCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
