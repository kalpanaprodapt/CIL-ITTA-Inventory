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
 * Circuit Order - Circuits domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_circuit_order",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_circuit_order_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_circuit_order_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_circuit_order_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_circuit_order_ext", columnList = "external_ref")
        })
public class CircuitOrder extends BaseEntity {

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

    @Column(name = "circuit_id", nullable = true)
    private UUID circuitId;

    @Column(name = "order_number", nullable = false, length = 255)
    private String orderNumber;

    @Column(name = "ordered_on", nullable = false)
    private LocalDate orderedOn;

    @Column(name = "requested_date", nullable = true)
    private LocalDate requestedDate;

    @Column(name = "committed_date", nullable = true)
    private LocalDate committedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = true, length = 40)
    private CircuitOrderOrderStatus orderStatus;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private CircuitOrderLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    public CircuitOrder() {
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

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public LocalDate getOrderedOn() { return orderedOn; }
    public void setOrderedOn(LocalDate orderedOn) { this.orderedOn = orderedOn; }

    public LocalDate getRequestedDate() { return requestedDate; }
    public void setRequestedDate(LocalDate requestedDate) { this.requestedDate = requestedDate; }

    public LocalDate getCommittedDate() { return committedDate; }
    public void setCommittedDate(LocalDate committedDate) { this.committedDate = committedDate; }

    public CircuitOrderOrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(CircuitOrderOrderStatus orderStatus) { this.orderStatus = orderStatus; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public CircuitOrderLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(CircuitOrderLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

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
        if (!(o instanceof CircuitOrder other)) {
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
        return "CircuitOrder{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "circuitId=" + circuitId + ", " +
                "orderNumber=" + orderNumber + ", " +
                "orderedOn=" + orderedOn + ", " +
                "requestedDate=" + requestedDate + ", " +
                "committedDate=" + committedDate + ", " +
                "orderStatus=" + orderStatus + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + "}";
    }
}
