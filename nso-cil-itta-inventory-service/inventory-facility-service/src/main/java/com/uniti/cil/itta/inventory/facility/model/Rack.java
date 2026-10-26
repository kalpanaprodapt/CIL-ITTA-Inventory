package com.uniti.cil.itta.inventory.facility.model;

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
 * Rack - Facility domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_rack",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_rack_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_rack_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_rack_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_rack_ext", columnList = "external_ref")
        })
public class Rack extends BaseEntity {

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

    @Column(name = "room_id", nullable = false)
    private UUID roomId;

    @Column(name = "rack_units", nullable = false)
    private Integer rackUnits;

    @Column(name = "width_mm", nullable = true)
    private Integer widthMm;

    @Column(name = "depth_mm", nullable = true)
    private Integer depthMm;

    @Column(name = "max_weight_kg", nullable = true, precision = 18, scale = 4)
    private BigDecimal maxWeightKg;

    @Column(name = "row_label", nullable = true, length = 255)
    private String rowLabel;

    @Column(name = "position", nullable = true, length = 255)
    private String position;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private RackLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public Rack() {
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

    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }

    public Integer getRackUnits() { return rackUnits; }
    public void setRackUnits(Integer rackUnits) { this.rackUnits = rackUnits; }

    public Integer getWidthMm() { return widthMm; }
    public void setWidthMm(Integer widthMm) { this.widthMm = widthMm; }

    public Integer getDepthMm() { return depthMm; }
    public void setDepthMm(Integer depthMm) { this.depthMm = depthMm; }

    public BigDecimal getMaxWeightKg() { return maxWeightKg; }
    public void setMaxWeightKg(BigDecimal maxWeightKg) { this.maxWeightKg = maxWeightKg; }

    public String getRowLabel() { return rowLabel; }
    public void setRowLabel(String rowLabel) { this.rowLabel = rowLabel; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public RackLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(RackLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rack other)) {
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
        return "Rack{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "roomId=" + roomId + ", " +
                "rackUnits=" + rackUnits + ", " +
                "widthMm=" + widthMm + ", " +
                "depthMm=" + depthMm + ", " +
                "maxWeightKg=" + maxWeightKg + ", " +
                "rowLabel=" + rowLabel + ", " +
                "position=" + position + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + "}";
    }
}
