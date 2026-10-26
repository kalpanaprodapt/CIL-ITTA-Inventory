package com.uniti.cil.itta.inventory.circuit.model;

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
 * Circuit Termination - Circuits domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_circuit_termination",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_circuit_termination_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_circuit_termination_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_circuit_termination_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_circuit_termination_ext", columnList = "external_ref")
        })
public class CircuitTermination extends BaseEntity {

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

    @Column(name = "circuit_id", nullable = false)
    private UUID circuitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "side", nullable = true, length = 40)
    private CircuitTerminationSide side;

    @Column(name = "port_id", nullable = false)
    private UUID portId;

    @Column(name = "xconnect_id", nullable = true, length = 255)
    private String xconnectId;

    @Column(name = "patch_info", nullable = true, length = 255)
    private String patchInfo;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    public CircuitTermination() {
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

    public UUID getCircuitId() { return circuitId; }
    public void setCircuitId(UUID circuitId) { this.circuitId = circuitId; }

    public CircuitTerminationSide getSide() { return side; }
    public void setSide(CircuitTerminationSide side) { this.side = side; }

    public UUID getPortId() { return portId; }
    public void setPortId(UUID portId) { this.portId = portId; }

    public String getXconnectId() { return xconnectId; }
    public void setXconnectId(String xconnectId) { this.xconnectId = xconnectId; }

    public String getPatchInfo() { return patchInfo; }
    public void setPatchInfo(String patchInfo) { this.patchInfo = patchInfo; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CircuitTermination other)) {
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
        return "CircuitTermination{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "circuitId=" + circuitId + ", " +
                "side=" + side + ", " +
                "portId=" + portId + ", " +
                "xconnectId=" + xconnectId + ", " +
                "patchInfo=" + patchInfo + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + "}";
    }
}
