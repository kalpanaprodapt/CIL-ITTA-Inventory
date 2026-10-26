package com.uniti.cil.itta.inventory.capacity.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Reservation. */
public class ReservationCreateRequest {

    @NotBlank @Size(max = 255)
    private String code;
    @NotBlank @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    @NotNull
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    @NotBlank @Size(max = 255)
    private String resourceRef;
    @NotBlank @Size(max = 255)
    private String reservedFor;
    @NotNull @Digits(integer = 14, fraction = 4)
    private BigDecimal quantity;
    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
    @Size(max = 4000)
    private String notes;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
    private ReservationLifecycleStage lifecycleStage;
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LifecycleStatus getStatus() { return status; }
    public void setStatus(LifecycleStatus status) { this.status = status; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public String getResourceRef() { return resourceRef; }
    public void setResourceRef(String resourceRef) { this.resourceRef = resourceRef; }
    public String getReservedFor() { return reservedFor; }
    public void setReservedFor(String reservedFor) { this.reservedFor = reservedFor; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public ReservationLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(ReservationLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
