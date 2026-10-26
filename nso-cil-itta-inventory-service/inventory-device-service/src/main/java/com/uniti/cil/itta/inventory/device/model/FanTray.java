package com.uniti.cil.itta.inventory.device.model;

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
 * Fan Tray - Network Device domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_fan_tray",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_fan_tray_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_fan_tray_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_fan_tray_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_fan_tray_ext", columnList = "external_ref")
        })
public class FanTray extends BaseEntity {

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

    @Column(name = "chassis_id", nullable = false)
    private UUID chassisId;

    @Column(name = "slot_number", nullable = true)
    private Integer slotNumber;

    @Column(name = "fan_count", nullable = true)
    private Integer fanCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "airflow", nullable = true, length = 40)
    private FanTrayAirflow airflow;

    @Column(name = "serial_number", nullable = true, length = 255)
    private String serialNumber;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private FanTrayLifecycleStage lifecycleStage;

    public FanTray() {
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

    public UUID getChassisId() { return chassisId; }
    public void setChassisId(UUID chassisId) { this.chassisId = chassisId; }

    public Integer getSlotNumber() { return slotNumber; }
    public void setSlotNumber(Integer slotNumber) { this.slotNumber = slotNumber; }

    public Integer getFanCount() { return fanCount; }
    public void setFanCount(Integer fanCount) { this.fanCount = fanCount; }

    public FanTrayAirflow getAirflow() { return airflow; }
    public void setAirflow(FanTrayAirflow airflow) { this.airflow = airflow; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public FanTrayLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(FanTrayLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FanTray other)) {
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
        return "FanTray{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "chassisId=" + chassisId + ", " +
                "slotNumber=" + slotNumber + ", " +
                "fanCount=" + fanCount + ", " +
                "airflow=" + airflow + ", " +
                "serialNumber=" + serialNumber + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + "}";
    }
}
