package com.uniti.cil.itta.inventory.cabling.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Splice search endpoint. */
public class SpliceSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String enclosureId;
    private UUID aStrandId;
    private UUID zStrandId;
    private BigDecimal lossDbMin;
    private BigDecimal lossDbMax;
    private LocalDate splicedOnFrom;
    private LocalDate splicedOnTo;
    private List<SpliceLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<SpliceCriticality> criticality;

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
    public String getEnclosureId() { return enclosureId; }
    public void setEnclosureId(String enclosureId) { this.enclosureId = enclosureId; }
    public UUID getAStrandId() { return aStrandId; }
    public void setAStrandId(UUID aStrandId) { this.aStrandId = aStrandId; }
    public UUID getZStrandId() { return zStrandId; }
    public void setZStrandId(UUID zStrandId) { this.zStrandId = zStrandId; }
    public BigDecimal getLossDbMin() { return lossDbMin; }
    public void setLossDbMin(BigDecimal lossDbMin) { this.lossDbMin = lossDbMin; }
    public BigDecimal getLossDbMax() { return lossDbMax; }
    public void setLossDbMax(BigDecimal lossDbMax) { this.lossDbMax = lossDbMax; }
    public LocalDate getSplicedOnFrom() { return splicedOnFrom; }
    public void setSplicedOnFrom(LocalDate splicedOnFrom) { this.splicedOnFrom = splicedOnFrom; }
    public LocalDate getSplicedOnTo() { return splicedOnTo; }
    public void setSplicedOnTo(LocalDate splicedOnTo) { this.splicedOnTo = splicedOnTo; }
    public List<SpliceLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<SpliceLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<SpliceCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<SpliceCriticality> criticality) { this.criticality = criticality; }}
