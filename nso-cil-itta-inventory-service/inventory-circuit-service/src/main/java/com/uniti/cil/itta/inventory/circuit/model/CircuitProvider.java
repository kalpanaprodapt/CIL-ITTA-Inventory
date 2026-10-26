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
 * Circuit Provider - Circuits domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_circuit_provider",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_circuit_provider_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_circuit_provider_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_circuit_provider_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_circuit_provider_ext", columnList = "external_ref")
        })
public class CircuitProvider extends BaseEntity {

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

    @Column(name = "provider_name", nullable = false, length = 255)
    private String providerName;

    @Column(name = "account_number", nullable = true, length = 255)
    private String accountNumber;

    @Column(name = "noc", nullable = true, length = 255)
    private String noc;

    @Column(name = "escalation_email", nullable = true, length = 255)
    private String escalationEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = true, length = 40)
    private CircuitProviderProviderType providerType;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private CircuitProviderCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    public CircuitProvider() {
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

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getNoc() { return noc; }
    public void setNoc(String noc) { this.noc = noc; }

    public String getEscalationEmail() { return escalationEmail; }
    public void setEscalationEmail(String escalationEmail) { this.escalationEmail = escalationEmail; }

    public CircuitProviderProviderType getProviderType() { return providerType; }
    public void setProviderType(CircuitProviderProviderType providerType) { this.providerType = providerType; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public CircuitProviderCriticality getCriticality() { return criticality; }
    public void setCriticality(CircuitProviderCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CircuitProvider other)) {
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
        return "CircuitProvider{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "providerName=" + providerName + ", " +
                "accountNumber=" + accountNumber + ", " +
                "noc=" + noc + ", " +
                "escalationEmail=" + escalationEmail + ", " +
                "providerType=" + providerType + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + "}";
    }
}
