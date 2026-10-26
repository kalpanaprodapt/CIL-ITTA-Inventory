package com.uniti.cil.itta.inventory.topology.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Link search endpoint. */
public class LinkSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID aInterfaceId;
    private UUID zInterfaceId;
    private List<LinkLinkType> linkType;
    private Long speedMbpsMin;
    private Long speedMbpsMax;
    private String discoveredBy;
    private String region;
    private String owner;
    private List<LinkCriticality> criticality;
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
    public UUID getAInterfaceId() { return aInterfaceId; }
    public void setAInterfaceId(UUID aInterfaceId) { this.aInterfaceId = aInterfaceId; }
    public UUID getZInterfaceId() { return zInterfaceId; }
    public void setZInterfaceId(UUID zInterfaceId) { this.zInterfaceId = zInterfaceId; }
    public List<LinkLinkType> getLinkType() { return linkType; }
    public void setLinkType(List<LinkLinkType> linkType) { this.linkType = linkType; }
    public Long getSpeedMbpsMin() { return speedMbpsMin; }
    public void setSpeedMbpsMin(Long speedMbpsMin) { this.speedMbpsMin = speedMbpsMin; }
    public Long getSpeedMbpsMax() { return speedMbpsMax; }
    public void setSpeedMbpsMax(Long speedMbpsMax) { this.speedMbpsMax = speedMbpsMax; }
    public String getDiscoveredBy() { return discoveredBy; }
    public void setDiscoveredBy(String discoveredBy) { this.discoveredBy = discoveredBy; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<LinkCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<LinkCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
