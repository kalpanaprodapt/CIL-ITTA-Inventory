package com.uniti.cil.itta.inventory.cabling.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Fiber Strand search endpoint. */
public class FiberStrandSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID cableId;
    private Integer strandNumberMin;
    private Integer strandNumberMax;
    private String bufferTube;
    private String tubeColor;
    private BigDecimal attenuationDbMin;
    private BigDecimal attenuationDbMax;
    private Boolean inUse;
    private String region;
    private String owner;
    private List<FiberStrandCriticality> criticality;
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
    public UUID getCableId() { return cableId; }
    public void setCableId(UUID cableId) { this.cableId = cableId; }
    public Integer getStrandNumberMin() { return strandNumberMin; }
    public void setStrandNumberMin(Integer strandNumberMin) { this.strandNumberMin = strandNumberMin; }
    public Integer getStrandNumberMax() { return strandNumberMax; }
    public void setStrandNumberMax(Integer strandNumberMax) { this.strandNumberMax = strandNumberMax; }
    public String getBufferTube() { return bufferTube; }
    public void setBufferTube(String bufferTube) { this.bufferTube = bufferTube; }
    public String getTubeColor() { return tubeColor; }
    public void setTubeColor(String tubeColor) { this.tubeColor = tubeColor; }
    public BigDecimal getAttenuationDbMin() { return attenuationDbMin; }
    public void setAttenuationDbMin(BigDecimal attenuationDbMin) { this.attenuationDbMin = attenuationDbMin; }
    public BigDecimal getAttenuationDbMax() { return attenuationDbMax; }
    public void setAttenuationDbMax(BigDecimal attenuationDbMax) { this.attenuationDbMax = attenuationDbMax; }
    public Boolean getInUse() { return inUse; }
    public void setInUse(Boolean inUse) { this.inUse = inUse; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<FiberStrandCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<FiberStrandCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
