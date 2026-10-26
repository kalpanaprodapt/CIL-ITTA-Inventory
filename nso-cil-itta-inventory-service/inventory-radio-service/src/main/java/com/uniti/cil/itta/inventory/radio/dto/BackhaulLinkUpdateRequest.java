package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Backhaul Link; null properties are left unchanged. */
public class BackhaulLinkUpdateRequest {

    private Long expectedVersion;

    @Size(max = 255)
    private String code;
    @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    private UUID baseStationId;
    private BackhaulLinkLinkMedium linkMedium;
    private Long capacityMbps;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal latencyMs;
    @Size(max = 255)
    private String circuitRef;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
    public Long getExpectedVersion() { return expectedVersion; }
    public void setExpectedVersion(Long expectedVersion) { this.expectedVersion = expectedVersion; }

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
    public UUID getBaseStationId() { return baseStationId; }
    public void setBaseStationId(UUID baseStationId) { this.baseStationId = baseStationId; }
    public BackhaulLinkLinkMedium getLinkMedium() { return linkMedium; }
    public void setLinkMedium(BackhaulLinkLinkMedium linkMedium) { this.linkMedium = linkMedium; }
    public Long getCapacityMbps() { return capacityMbps; }
    public void setCapacityMbps(Long capacityMbps) { this.capacityMbps = capacityMbps; }
    public BigDecimal getLatencyMs() { return latencyMs; }
    public void setLatencyMs(BigDecimal latencyMs) { this.latencyMs = latencyMs; }
    public String getCircuitRef() { return circuitRef; }
    public void setCircuitRef(String circuitRef) { this.circuitRef = circuitRef; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }}
