package com.uniti.cil.itta.inventory.assetmgmt.model;

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
 * Asset Tag - Asset and Finance domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_asset_tag",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_asset_tag_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_asset_tag_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_asset_tag_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_asset_tag_ext", columnList = "external_ref")
        })
public class AssetTag extends BaseEntity {

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

    @Column(name = "asset_id", nullable = false)
    private UUID assetId;

    @Column(name = "tag_value", nullable = false, length = 255)
    private String tagValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag_type", nullable = true, length = 40)
    private AssetTagTagType tagType;

    @Column(name = "printed_on", nullable = true)
    private LocalDate printedOn;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private AssetTagCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    public AssetTag() {
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

    public UUID getAssetId() { return assetId; }
    public void setAssetId(UUID assetId) { this.assetId = assetId; }

    public String getTagValue() { return tagValue; }
    public void setTagValue(String tagValue) { this.tagValue = tagValue; }

    public AssetTagTagType getTagType() { return tagType; }
    public void setTagType(AssetTagTagType tagType) { this.tagType = tagType; }

    public LocalDate getPrintedOn() { return printedOn; }
    public void setPrintedOn(LocalDate printedOn) { this.printedOn = printedOn; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public AssetTagCriticality getCriticality() { return criticality; }
    public void setCriticality(AssetTagCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetTag other)) {
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
        return "AssetTag{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "assetId=" + assetId + ", " +
                "tagValue=" + tagValue + ", " +
                "tagType=" + tagType + ", " +
                "printedOn=" + printedOn + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + "}";
    }
}
