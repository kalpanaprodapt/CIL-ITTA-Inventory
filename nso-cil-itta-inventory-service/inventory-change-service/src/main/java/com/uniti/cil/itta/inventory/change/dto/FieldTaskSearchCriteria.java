package com.uniti.cil.itta.inventory.change.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Field Task search endpoint. */
public class FieldTaskSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID workOrderId;
    private String taskName;
    private Integer sequenceMin;
    private Integer sequenceMax;
    private Integer durationMinutesMin;
    private Integer durationMinutesMax;
    private Instant completedOnFrom;
    private Instant completedOnTo;
    private String region;
    private String owner;
    private List<FieldTaskCriticality> criticality;
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
    public UUID getWorkOrderId() { return workOrderId; }
    public void setWorkOrderId(UUID workOrderId) { this.workOrderId = workOrderId; }
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public Integer getSequenceMin() { return sequenceMin; }
    public void setSequenceMin(Integer sequenceMin) { this.sequenceMin = sequenceMin; }
    public Integer getSequenceMax() { return sequenceMax; }
    public void setSequenceMax(Integer sequenceMax) { this.sequenceMax = sequenceMax; }
    public Integer getDurationMinutesMin() { return durationMinutesMin; }
    public void setDurationMinutesMin(Integer durationMinutesMin) { this.durationMinutesMin = durationMinutesMin; }
    public Integer getDurationMinutesMax() { return durationMinutesMax; }
    public void setDurationMinutesMax(Integer durationMinutesMax) { this.durationMinutesMax = durationMinutesMax; }
    public Instant getCompletedOnFrom() { return completedOnFrom; }
    public void setCompletedOnFrom(Instant completedOnFrom) { this.completedOnFrom = completedOnFrom; }
    public Instant getCompletedOnTo() { return completedOnTo; }
    public void setCompletedOnTo(Instant completedOnTo) { this.completedOnTo = completedOnTo; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<FieldTaskCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<FieldTaskCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
