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

/** Partial update payload for a Base Station; null properties are left unchanged. */
public class BaseStationUpdateRequest {

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
    private UUID siteId;
    private Long enbId;
    private BaseStationTechnology technology;
    private Integer sectorCount;
    private Integer cellCount;
    @Size(max = 255)
    private String labels;
    private BaseStationLifecycleStage lifecycleStage;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
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
    public UUID getSiteId() { return siteId; }
    public void setSiteId(UUID siteId) { this.siteId = siteId; }
    public Long getEnbId() { return enbId; }
    public void setEnbId(Long enbId) { this.enbId = enbId; }
    public BaseStationTechnology getTechnology() { return technology; }
    public void setTechnology(BaseStationTechnology technology) { this.technology = technology; }
    public Integer getSectorCount() { return sectorCount; }
    public void setSectorCount(Integer sectorCount) { this.sectorCount = sectorCount; }
    public Integer getCellCount() { return cellCount; }
    public void setCellCount(Integer cellCount) { this.cellCount = cellCount; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public BaseStationLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(BaseStationLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
