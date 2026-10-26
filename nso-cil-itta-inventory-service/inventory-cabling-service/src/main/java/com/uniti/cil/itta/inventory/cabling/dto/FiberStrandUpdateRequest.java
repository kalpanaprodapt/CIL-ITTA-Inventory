package com.uniti.cil.itta.inventory.cabling.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Fiber Strand; null properties are left unchanged. */
public class FiberStrandUpdateRequest {

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
    private UUID cableId;
    private Integer strandNumber;
    @Size(max = 255)
    private String bufferTube;
    @Size(max = 255)
    private String tubeColor;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal attenuationDb;
    private Boolean inUse;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private FiberStrandCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
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
    public UUID getCableId() { return cableId; }
    public void setCableId(UUID cableId) { this.cableId = cableId; }
    public Integer getStrandNumber() { return strandNumber; }
    public void setStrandNumber(Integer strandNumber) { this.strandNumber = strandNumber; }
    public String getBufferTube() { return bufferTube; }
    public void setBufferTube(String bufferTube) { this.bufferTube = bufferTube; }
    public String getTubeColor() { return tubeColor; }
    public void setTubeColor(String tubeColor) { this.tubeColor = tubeColor; }
    public BigDecimal getAttenuationDb() { return attenuationDb; }
    public void setAttenuationDb(BigDecimal attenuationDb) { this.attenuationDb = attenuationDb; }
    public Boolean getInUse() { return inUse; }
    public void setInUse(Boolean inUse) { this.inUse = inUse; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public FiberStrandCriticality getCriticality() { return criticality; }
    public void setCriticality(FiberStrandCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }}
