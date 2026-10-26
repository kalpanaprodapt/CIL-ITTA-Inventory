package com.uniti.cil.itta.inventory.software.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Software Version search endpoint. */
public class SoftwareVersionSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID imageId;
    private String versionString;
    private LocalDate releaseDateFrom;
    private LocalDate releaseDateTo;
    private LocalDate endOfSupportFrom;
    private LocalDate endOfSupportTo;
    private List<SoftwareVersionReleaseTrain> releaseTrain;
    private Integer cveCountMin;
    private Integer cveCountMax;
    private String region;
    private String owner;
    private List<SoftwareVersionCriticality> criticality;
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
    public UUID getImageId() { return imageId; }
    public void setImageId(UUID imageId) { this.imageId = imageId; }
    public String getVersionString() { return versionString; }
    public void setVersionString(String versionString) { this.versionString = versionString; }
    public LocalDate getReleaseDateFrom() { return releaseDateFrom; }
    public void setReleaseDateFrom(LocalDate releaseDateFrom) { this.releaseDateFrom = releaseDateFrom; }
    public LocalDate getReleaseDateTo() { return releaseDateTo; }
    public void setReleaseDateTo(LocalDate releaseDateTo) { this.releaseDateTo = releaseDateTo; }
    public LocalDate getEndOfSupportFrom() { return endOfSupportFrom; }
    public void setEndOfSupportFrom(LocalDate endOfSupportFrom) { this.endOfSupportFrom = endOfSupportFrom; }
    public LocalDate getEndOfSupportTo() { return endOfSupportTo; }
    public void setEndOfSupportTo(LocalDate endOfSupportTo) { this.endOfSupportTo = endOfSupportTo; }
    public List<SoftwareVersionReleaseTrain> getReleaseTrain() { return releaseTrain; }
    public void setReleaseTrain(List<SoftwareVersionReleaseTrain> releaseTrain) { this.releaseTrain = releaseTrain; }
    public Integer getCveCountMin() { return cveCountMin; }
    public void setCveCountMin(Integer cveCountMin) { this.cveCountMin = cveCountMin; }
    public Integer getCveCountMax() { return cveCountMax; }
    public void setCveCountMax(Integer cveCountMax) { this.cveCountMax = cveCountMax; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<SoftwareVersionCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<SoftwareVersionCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
