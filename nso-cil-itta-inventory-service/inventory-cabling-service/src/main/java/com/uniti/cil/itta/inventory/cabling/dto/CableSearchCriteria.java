package com.uniti.cil.itta.inventory.cabling.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Cable search endpoint. */
public class CableSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private List<CableCableType> cableType;
    private BigDecimal lengthMMin;
    private BigDecimal lengthMMax;
    private UUID aEndPortId;
    private UUID zEndPortId;
    private String color;
    private String label;
    private List<CableCriticality> criticality;
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
    public List<CableCableType> getCableType() { return cableType; }
    public void setCableType(List<CableCableType> cableType) { this.cableType = cableType; }
    public BigDecimal getLengthMMin() { return lengthMMin; }
    public void setLengthMMin(BigDecimal lengthMMin) { this.lengthMMin = lengthMMin; }
    public BigDecimal getLengthMMax() { return lengthMMax; }
    public void setLengthMMax(BigDecimal lengthMMax) { this.lengthMMax = lengthMMax; }
    public UUID getAEndPortId() { return aEndPortId; }
    public void setAEndPortId(UUID aEndPortId) { this.aEndPortId = aEndPortId; }
    public UUID getZEndPortId() { return zEndPortId; }
    public void setZEndPortId(UUID zEndPortId) { this.zEndPortId = zEndPortId; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public List<CableCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<CableCriticality> criticality) { this.criticality = criticality; }
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
