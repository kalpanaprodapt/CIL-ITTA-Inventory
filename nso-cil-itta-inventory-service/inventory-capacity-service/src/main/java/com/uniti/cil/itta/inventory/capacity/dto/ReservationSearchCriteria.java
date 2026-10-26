package com.uniti.cil.itta.inventory.capacity.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Reservation search endpoint. */
public class ReservationSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String resourceRef;
    private String reservedFor;
    private BigDecimal quantityMin;
    private BigDecimal quantityMax;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate endDateFrom;
    private LocalDate endDateTo;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<ReservationLifecycleStage> lifecycleStage;

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
    public String getResourceRef() { return resourceRef; }
    public void setResourceRef(String resourceRef) { this.resourceRef = resourceRef; }
    public String getReservedFor() { return reservedFor; }
    public void setReservedFor(String reservedFor) { this.reservedFor = reservedFor; }
    public BigDecimal getQuantityMin() { return quantityMin; }
    public void setQuantityMin(BigDecimal quantityMin) { this.quantityMin = quantityMin; }
    public BigDecimal getQuantityMax() { return quantityMax; }
    public void setQuantityMax(BigDecimal quantityMax) { this.quantityMax = quantityMax; }
    public LocalDate getStartDateFrom() { return startDateFrom; }
    public void setStartDateFrom(LocalDate startDateFrom) { this.startDateFrom = startDateFrom; }
    public LocalDate getStartDateTo() { return startDateTo; }
    public void setStartDateTo(LocalDate startDateTo) { this.startDateTo = startDateTo; }
    public LocalDate getEndDateFrom() { return endDateFrom; }
    public void setEndDateFrom(LocalDate endDateFrom) { this.endDateFrom = endDateFrom; }
    public LocalDate getEndDateTo() { return endDateTo; }
    public void setEndDateTo(LocalDate endDateTo) { this.endDateTo = endDateTo; }
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
    public List<ReservationLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<ReservationLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
