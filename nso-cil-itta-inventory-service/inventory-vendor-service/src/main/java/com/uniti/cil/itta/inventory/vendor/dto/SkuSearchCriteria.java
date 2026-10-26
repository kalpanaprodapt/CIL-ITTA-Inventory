package com.uniti.cil.itta.inventory.vendor.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Sku search endpoint. */
public class SkuSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID modelId;
    private String skuCode;
    private BigDecimal listPriceMin;
    private BigDecimal listPriceMax;
    private String currency;
    private Integer leadTimeDaysMin;
    private Integer leadTimeDaysMax;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<SkuCriticality> criticality;
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
    public UUID getModelId() { return modelId; }
    public void setModelId(UUID modelId) { this.modelId = modelId; }
    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }
    public BigDecimal getListPriceMin() { return listPriceMin; }
    public void setListPriceMin(BigDecimal listPriceMin) { this.listPriceMin = listPriceMin; }
    public BigDecimal getListPriceMax() { return listPriceMax; }
    public void setListPriceMax(BigDecimal listPriceMax) { this.listPriceMax = listPriceMax; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Integer getLeadTimeDaysMin() { return leadTimeDaysMin; }
    public void setLeadTimeDaysMin(Integer leadTimeDaysMin) { this.leadTimeDaysMin = leadTimeDaysMin; }
    public Integer getLeadTimeDaysMax() { return leadTimeDaysMax; }
    public void setLeadTimeDaysMax(Integer leadTimeDaysMax) { this.leadTimeDaysMax = leadTimeDaysMax; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<SkuCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<SkuCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
