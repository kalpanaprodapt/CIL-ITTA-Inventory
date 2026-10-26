package com.uniti.cil.itta.inventory.topology.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Topology Node search endpoint. */
public class TopologyNodeSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID viewId;
    private String entityRef;
    private String nodeType;
    private BigDecimal xPosMin;
    private BigDecimal xPosMax;
    private BigDecimal yPosMin;
    private BigDecimal yPosMax;
    private Integer layerMin;
    private Integer layerMax;
    private List<TopologyNodeCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;

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
    public UUID getViewId() { return viewId; }
    public void setViewId(UUID viewId) { this.viewId = viewId; }
    public String getEntityRef() { return entityRef; }
    public void setEntityRef(String entityRef) { this.entityRef = entityRef; }
    public String getNodeType() { return nodeType; }
    public void setNodeType(String nodeType) { this.nodeType = nodeType; }
    public BigDecimal getXPosMin() { return xPosMin; }
    public void setXPosMin(BigDecimal xPosMin) { this.xPosMin = xPosMin; }
    public BigDecimal getXPosMax() { return xPosMax; }
    public void setXPosMax(BigDecimal xPosMax) { this.xPosMax = xPosMax; }
    public BigDecimal getYPosMin() { return yPosMin; }
    public void setYPosMin(BigDecimal yPosMin) { this.yPosMin = yPosMin; }
    public BigDecimal getYPosMax() { return yPosMax; }
    public void setYPosMax(BigDecimal yPosMax) { this.yPosMax = yPosMax; }
    public Integer getLayerMin() { return layerMin; }
    public void setLayerMin(Integer layerMin) { this.layerMin = layerMin; }
    public Integer getLayerMax() { return layerMax; }
    public void setLayerMax(Integer layerMax) { this.layerMax = layerMax; }
    public List<TopologyNodeCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<TopologyNodeCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }}
