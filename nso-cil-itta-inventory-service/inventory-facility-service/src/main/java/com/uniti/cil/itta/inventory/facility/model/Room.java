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
 * Room - Facility domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_room",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_room_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_room_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_room_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_room_ext", columnList = "external_ref")
        })
public class Room extends BaseEntity {

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

    @Column(name = "floor_id", nullable = false)
    private UUID floorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = true, length = 40)
    private RoomRoomType roomType;

    @Column(name = "area_sqm", nullable = true, precision = 18, scale = 4)
    private BigDecimal areaSqm;

    @Column(name = "cooling_tons", nullable = true, precision = 18, scale = 4)
    private BigDecimal coolingTons;

    @Column(name = "fire_zone", nullable = true, length = 255)
    private String fireZone;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private RoomLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    public Room() {
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

    public UUID getFloorId() { return floorId; }
    public void setFloorId(UUID floorId) { this.floorId = floorId; }

    public RoomRoomType getRoomType() { return roomType; }
    public void setRoomType(RoomRoomType roomType) { this.roomType = roomType; }

    public BigDecimal getAreaSqm() { return areaSqm; }
    public void setAreaSqm(BigDecimal areaSqm) { this.areaSqm = areaSqm; }

    public BigDecimal getCoolingTons() { return coolingTons; }
    public void setCoolingTons(BigDecimal coolingTons) { this.coolingTons = coolingTons; }

    public String getFireZone() { return fireZone; }
    public void setFireZone(String fireZone) { this.fireZone = fireZone; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public RoomLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(RoomLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room other)) {
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
        return "Room{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "floorId=" + floorId + ", " +
                "roomType=" + roomType + ", " +
                "areaSqm=" + areaSqm + ", " +
                "coolingTons=" + coolingTons + ", " +
                "fireZone=" + fireZone + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + "}";
    }
}
