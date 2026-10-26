package com.uniti.cil.itta.inventory.change.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Field Task. */
public class FieldTaskCreateRequest {

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
    @NotNull
    private UUID workOrderId;
    @NotBlank @Size(max = 255)
    private String taskName;
    @NotNull
    private Integer sequence;
    private Integer durationMinutes;
    private Instant completedOn;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private FieldTaskCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
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
    public UUID getWorkOrderId() { return workOrderId; }
    public void setWorkOrderId(UUID workOrderId) { this.workOrderId = workOrderId; }
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public Integer getSequence() { return sequence; }
    public void setSequence(Integer sequence) { this.sequence = sequence; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public Instant getCompletedOn() { return completedOn; }
    public void setCompletedOn(Instant completedOn) { this.completedOn = completedOn; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public FieldTaskCriticality getCriticality() { return criticality; }
    public void setCriticality(FieldTaskCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }}
