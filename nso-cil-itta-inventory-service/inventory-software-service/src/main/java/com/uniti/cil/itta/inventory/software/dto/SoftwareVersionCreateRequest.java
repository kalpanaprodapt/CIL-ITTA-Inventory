package com.uniti.cil.itta.inventory.software.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Software Version. */
public class SoftwareVersionCreateRequest {

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
    private UUID imageId;
    @NotBlank @Size(max = 255)
    private String versionString;
    private LocalDate releaseDate;
    private LocalDate endOfSupport;
    private SoftwareVersionReleaseTrain releaseTrain;
    private Integer cveCount;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private SoftwareVersionCriticality criticality;
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
    public UUID getImageId() { return imageId; }
    public void setImageId(UUID imageId) { this.imageId = imageId; }
    public String getVersionString() { return versionString; }
    public void setVersionString(String versionString) { this.versionString = versionString; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public LocalDate getEndOfSupport() { return endOfSupport; }
    public void setEndOfSupport(LocalDate endOfSupport) { this.endOfSupport = endOfSupport; }
    public SoftwareVersionReleaseTrain getReleaseTrain() { return releaseTrain; }
    public void setReleaseTrain(SoftwareVersionReleaseTrain releaseTrain) { this.releaseTrain = releaseTrain; }
    public Integer getCveCount() { return cveCount; }
    public void setCveCount(Integer cveCount) { this.cveCount = cveCount; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public SoftwareVersionCriticality getCriticality() { return criticality; }
    public void setCriticality(SoftwareVersionCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }}
