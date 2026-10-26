package com.uniti.cil.itta.inventory.change.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Work Order search endpoint. */
public class WorkOrderSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String workOrderNumber;
    private String workType;
    private UUID assignedTechnicianId;
    private LocalDate dueDateFrom;
    private LocalDate dueDateTo;
    private List<WorkOrderWorkState> workState;
    private List<WorkOrderLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<WorkOrderCriticality> criticality;

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
    public String getWorkOrderNumber() { return workOrderNumber; }
    public void setWorkOrderNumber(String workOrderNumber) { this.workOrderNumber = workOrderNumber; }
    public String getWorkType() { return workType; }
    public void setWorkType(String workType) { this.workType = workType; }
    public UUID getAssignedTechnicianId() { return assignedTechnicianId; }
    public void setAssignedTechnicianId(UUID assignedTechnicianId) { this.assignedTechnicianId = assignedTechnicianId; }
    public LocalDate getDueDateFrom() { return dueDateFrom; }
    public void setDueDateFrom(LocalDate dueDateFrom) { this.dueDateFrom = dueDateFrom; }
    public LocalDate getDueDateTo() { return dueDateTo; }
    public void setDueDateTo(LocalDate dueDateTo) { this.dueDateTo = dueDateTo; }
    public List<WorkOrderWorkState> getWorkState() { return workState; }
    public void setWorkState(List<WorkOrderWorkState> workState) { this.workState = workState; }
    public List<WorkOrderLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<WorkOrderLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<WorkOrderCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<WorkOrderCriticality> criticality) { this.criticality = criticality; }}
