package com.uniti.cil.itta.inventory.circuit.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Circuit Order search endpoint. */
public class CircuitOrderSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID circuitId;
    private String orderNumber;
    private LocalDate orderedOnFrom;
    private LocalDate orderedOnTo;
    private LocalDate requestedDateFrom;
    private LocalDate requestedDateTo;
    private LocalDate committedDateFrom;
    private LocalDate committedDateTo;
    private List<CircuitOrderOrderStatus> orderStatus;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<CircuitOrderLifecycleStage> lifecycleStage;
    private String sourceSystem;

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
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public LocalDate getOrderedOnFrom() { return orderedOnFrom; }
    public void setOrderedOnFrom(LocalDate orderedOnFrom) { this.orderedOnFrom = orderedOnFrom; }
    public LocalDate getOrderedOnTo() { return orderedOnTo; }
    public void setOrderedOnTo(LocalDate orderedOnTo) { this.orderedOnTo = orderedOnTo; }
    public LocalDate getRequestedDateFrom() { return requestedDateFrom; }
    public void setRequestedDateFrom(LocalDate requestedDateFrom) { this.requestedDateFrom = requestedDateFrom; }
    public LocalDate getRequestedDateTo() { return requestedDateTo; }
    public void setRequestedDateTo(LocalDate requestedDateTo) { this.requestedDateTo = requestedDateTo; }
    public LocalDate getCommittedDateFrom() { return committedDateFrom; }
    public void setCommittedDateFrom(LocalDate committedDateFrom) { this.committedDateFrom = committedDateFrom; }
    public LocalDate getCommittedDateTo() { return committedDateTo; }
    public void setCommittedDateTo(LocalDate committedDateTo) { this.committedDateTo = committedDateTo; }
    public List<CircuitOrderOrderStatus> getOrderStatus() { return orderStatus; }
    public void setOrderStatus(List<CircuitOrderOrderStatus> orderStatus) { this.orderStatus = orderStatus; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<CircuitOrderLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<CircuitOrderLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
