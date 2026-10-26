package com.uniti.cil.itta.inventory.optical.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Otn Link; null properties are left unchanged. */
public class OtnLinkUpdateRequest {

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
    private UUID aNodeId;
    private UUID zNodeId;
    private OtnLinkOdu odu;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal latencyUs;
    @Size(max = 255)
    private String protection;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
    private OtnLinkLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
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
    public UUID getANodeId() { return aNodeId; }
    public void setANodeId(UUID aNodeId) { this.aNodeId = aNodeId; }
    public UUID getZNodeId() { return zNodeId; }
    public void setZNodeId(UUID zNodeId) { this.zNodeId = zNodeId; }
    public OtnLinkOdu getOdu() { return odu; }
    public void setOdu(OtnLinkOdu odu) { this.odu = odu; }
    public BigDecimal getLatencyUs() { return latencyUs; }
    public void setLatencyUs(BigDecimal latencyUs) { this.latencyUs = latencyUs; }
    public String getProtection() { return protection; }
    public void setProtection(String protection) { this.protection = protection; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public OtnLinkLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(OtnLinkLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }}
