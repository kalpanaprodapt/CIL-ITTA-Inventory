package com.uniti.cil.itta.inventory.integration.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Import Batch search endpoint. */
public class ImportBatchSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String fileName;
    private String entityType;
    private Integer totalRowsMin;
    private Integer totalRowsMax;
    private Integer processedRowsMin;
    private Integer processedRowsMax;
    private Integer failedRowsMin;
    private Integer failedRowsMax;
    private List<ImportBatchBatchState> batchState;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<ImportBatchLifecycleStage> lifecycleStage;
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
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public Integer getTotalRowsMin() { return totalRowsMin; }
    public void setTotalRowsMin(Integer totalRowsMin) { this.totalRowsMin = totalRowsMin; }
    public Integer getTotalRowsMax() { return totalRowsMax; }
    public void setTotalRowsMax(Integer totalRowsMax) { this.totalRowsMax = totalRowsMax; }
    public Integer getProcessedRowsMin() { return processedRowsMin; }
    public void setProcessedRowsMin(Integer processedRowsMin) { this.processedRowsMin = processedRowsMin; }
    public Integer getProcessedRowsMax() { return processedRowsMax; }
    public void setProcessedRowsMax(Integer processedRowsMax) { this.processedRowsMax = processedRowsMax; }
    public Integer getFailedRowsMin() { return failedRowsMin; }
    public void setFailedRowsMin(Integer failedRowsMin) { this.failedRowsMin = failedRowsMin; }
    public Integer getFailedRowsMax() { return failedRowsMax; }
    public void setFailedRowsMax(Integer failedRowsMax) { this.failedRowsMax = failedRowsMax; }
    public List<ImportBatchBatchState> getBatchState() { return batchState; }
    public void setBatchState(List<ImportBatchBatchState> batchState) { this.batchState = batchState; }
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
    public List<ImportBatchLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<ImportBatchLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
