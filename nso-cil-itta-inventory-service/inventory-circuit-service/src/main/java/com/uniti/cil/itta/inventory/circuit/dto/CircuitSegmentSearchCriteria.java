package com.uniti.cil.itta.inventory.circuit.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Circuit Segment search endpoint. */
public class CircuitSegmentSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID circuitId;
    private Integer segmentOrderMin;
    private Integer segmentOrderMax;
    private UUID aTerminationId;
    private UUID zTerminationId;
    private BigDecimal latencyMsMin;
    private BigDecimal latencyMsMax;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<CircuitSegmentCriticality> criticality;
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
    public UUID getCircuitId() { return circuitId; }
    public void setCircuitId(UUID circuitId) { this.circuitId = circuitId; }
    public Integer getSegmentOrderMin() { return segmentOrderMin; }
    public void setSegmentOrderMin(Integer segmentOrderMin) { this.segmentOrderMin = segmentOrderMin; }
    public Integer getSegmentOrderMax() { return segmentOrderMax; }
    public void setSegmentOrderMax(Integer segmentOrderMax) { this.segmentOrderMax = segmentOrderMax; }
    public UUID getATerminationId() { return aTerminationId; }
    public void setATerminationId(UUID aTerminationId) { this.aTerminationId = aTerminationId; }
    public UUID getZTerminationId() { return zTerminationId; }
    public void setZTerminationId(UUID zTerminationId) { this.zTerminationId = zTerminationId; }
    public BigDecimal getLatencyMsMin() { return latencyMsMin; }
    public void setLatencyMsMin(BigDecimal latencyMsMin) { this.latencyMsMin = latencyMsMin; }
    public BigDecimal getLatencyMsMax() { return latencyMsMax; }
    public void setLatencyMsMax(BigDecimal latencyMsMax) { this.latencyMsMax = latencyMsMax; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<CircuitSegmentCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<CircuitSegmentCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
