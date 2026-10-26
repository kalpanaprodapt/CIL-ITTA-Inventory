package com.uniti.cil.itta.inventory.service.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Service Catalog Item search endpoint. */
public class ServiceCatalogItemSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String itemName;
    private List<ServiceCatalogItemFamily> family;
    private BigDecimal basePriceMin;
    private BigDecimal basePriceMax;
    private String billingUnit;
    private Boolean orderable;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<ServiceCatalogItemLifecycleStage> lifecycleStage;
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
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public List<ServiceCatalogItemFamily> getFamily() { return family; }
    public void setFamily(List<ServiceCatalogItemFamily> family) { this.family = family; }
    public BigDecimal getBasePriceMin() { return basePriceMin; }
    public void setBasePriceMin(BigDecimal basePriceMin) { this.basePriceMin = basePriceMin; }
    public BigDecimal getBasePriceMax() { return basePriceMax; }
    public void setBasePriceMax(BigDecimal basePriceMax) { this.basePriceMax = basePriceMax; }
    public String getBillingUnit() { return billingUnit; }
    public void setBillingUnit(String billingUnit) { this.billingUnit = billingUnit; }
    public Boolean getOrderable() { return orderable; }
    public void setOrderable(Boolean orderable) { this.orderable = orderable; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<ServiceCatalogItemLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<ServiceCatalogItemLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
