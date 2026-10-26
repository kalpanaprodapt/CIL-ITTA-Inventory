package com.uniti.cil.itta.inventory.optical.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Roadm search endpoint. */
public class RoadmSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID systemId;
    private Integer degreesMin;
    private Integer degreesMax;
    private Boolean colorless;
    private Boolean directionless;
    private Boolean contentionless;
    private String region;
    private String owner;
    private List<RoadmCriticality> criticality;
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
    public UUID getSystemId() { return systemId; }
    public void setSystemId(UUID systemId) { this.systemId = systemId; }
    public Integer getDegreesMin() { return degreesMin; }
    public void setDegreesMin(Integer degreesMin) { this.degreesMin = degreesMin; }
    public Integer getDegreesMax() { return degreesMax; }
    public void setDegreesMax(Integer degreesMax) { this.degreesMax = degreesMax; }
    public Boolean getColorless() { return colorless; }
    public void setColorless(Boolean colorless) { this.colorless = colorless; }
    public Boolean getDirectionless() { return directionless; }
    public void setDirectionless(Boolean directionless) { this.directionless = directionless; }
    public Boolean getContentionless() { return contentionless; }
    public void setContentionless(Boolean contentionless) { this.contentionless = contentionless; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<RoadmCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<RoadmCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
