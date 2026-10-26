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

/** Payload used to create a Service Catalog Item. */
public class ServiceCatalogItemCreateRequest {

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
    @NotBlank @Size(max = 255)
    private String itemName;
    private ServiceCatalogItemFamily family;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal basePrice;
    @Size(max = 255)
    private String billingUnit;
    private Boolean orderable;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
    private ServiceCatalogItemLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
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
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public ServiceCatalogItemFamily getFamily() { return family; }
    public void setFamily(ServiceCatalogItemFamily family) { this.family = family; }
    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
    public String getBillingUnit() { return billingUnit; }
    public void setBillingUnit(String billingUnit) { this.billingUnit = billingUnit; }
    public Boolean getOrderable() { return orderable; }
    public void setOrderable(Boolean orderable) { this.orderable = orderable; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public ServiceCatalogItemLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(ServiceCatalogItemLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
