package com.uniti.cil.itta.inventory.assetmgmt.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Asset search endpoint. */
public class AssetSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String assetTag;
    private String assetClass;
    private LocalDate acquiredOnFrom;
    private LocalDate acquiredOnTo;
    private BigDecimal purchasePriceMin;
    private BigDecimal purchasePriceMax;
    private BigDecimal currentValueMin;
    private BigDecimal currentValueMax;
    private String custodian;
    private String labels;
    private List<AssetLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;

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
    public String getAssetTag() { return assetTag; }
    public void setAssetTag(String assetTag) { this.assetTag = assetTag; }
    public String getAssetClass() { return assetClass; }
    public void setAssetClass(String assetClass) { this.assetClass = assetClass; }
    public LocalDate getAcquiredOnFrom() { return acquiredOnFrom; }
    public void setAcquiredOnFrom(LocalDate acquiredOnFrom) { this.acquiredOnFrom = acquiredOnFrom; }
    public LocalDate getAcquiredOnTo() { return acquiredOnTo; }
    public void setAcquiredOnTo(LocalDate acquiredOnTo) { this.acquiredOnTo = acquiredOnTo; }
    public BigDecimal getPurchasePriceMin() { return purchasePriceMin; }
    public void setPurchasePriceMin(BigDecimal purchasePriceMin) { this.purchasePriceMin = purchasePriceMin; }
    public BigDecimal getPurchasePriceMax() { return purchasePriceMax; }
    public void setPurchasePriceMax(BigDecimal purchasePriceMax) { this.purchasePriceMax = purchasePriceMax; }
    public BigDecimal getCurrentValueMin() { return currentValueMin; }
    public void setCurrentValueMin(BigDecimal currentValueMin) { this.currentValueMin = currentValueMin; }
    public BigDecimal getCurrentValueMax() { return currentValueMax; }
    public void setCurrentValueMax(BigDecimal currentValueMax) { this.currentValueMax = currentValueMax; }
    public String getCustodian() { return custodian; }
    public void setCustodian(String custodian) { this.custodian = custodian; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<AssetLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<AssetLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
