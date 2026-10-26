package com.uniti.cil.itta.inventory.assetmgmt.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Asset Tag search endpoint. */
public class AssetTagSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID assetId;
    private String tagValue;
    private List<AssetTagTagType> tagType;
    private LocalDate printedOnFrom;
    private LocalDate printedOnTo;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<AssetTagCriticality> criticality;
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
    public UUID getAssetId() { return assetId; }
    public void setAssetId(UUID assetId) { this.assetId = assetId; }
    public String getTagValue() { return tagValue; }
    public void setTagValue(String tagValue) { this.tagValue = tagValue; }
    public List<AssetTagTagType> getTagType() { return tagType; }
    public void setTagType(List<AssetTagTagType> tagType) { this.tagType = tagType; }
    public LocalDate getPrintedOnFrom() { return printedOnFrom; }
    public void setPrintedOnFrom(LocalDate printedOnFrom) { this.printedOnFrom = printedOnFrom; }
    public LocalDate getPrintedOnTo() { return printedOnTo; }
    public void setPrintedOnTo(LocalDate printedOnTo) { this.printedOnTo = printedOnTo; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<AssetTagCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<AssetTagCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
