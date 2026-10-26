package com.uniti.cil.itta.inventory.software.model;

import com.uniti.cil.itta.inventory.common.model.BaseEntity;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/**
 * Software Version - Software and Licensing domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_software_version",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_software_version_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_software_version_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_software_version_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_software_version_ext", columnList = "external_ref")
        })
public class SoftwareVersion extends BaseEntity {

    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = true, length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LifecycleStatus status;

    @Column(name = "external_ref", nullable = true, length = 255)
    private String externalRef;

    @Column(name = "image_id", nullable = false)
    private UUID imageId;

    @Column(name = "version_string", nullable = false, length = 255)
    private String versionString;

    @Column(name = "release_date", nullable = true)
    private LocalDate releaseDate;

    @Column(name = "end_of_support", nullable = true)
    private LocalDate endOfSupport;

    @Enumerated(EnumType.STRING)
    @Column(name = "release_train", nullable = true, length = 40)
    private SoftwareVersionReleaseTrain releaseTrain;

    @Column(name = "cve_count", nullable = true)
    private Integer cveCount;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private SoftwareVersionCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    public SoftwareVersion() {
    }

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
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoftwareVersion other)) {
            return false;
        }
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "SoftwareVersion{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "imageId=" + imageId + ", " +
                "versionString=" + versionString + ", " +
                "releaseDate=" + releaseDate + ", " +
                "endOfSupport=" + endOfSupport + ", " +
                "releaseTrain=" + releaseTrain + ", " +
                "cveCount=" + cveCount + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + "}";
    }
}
