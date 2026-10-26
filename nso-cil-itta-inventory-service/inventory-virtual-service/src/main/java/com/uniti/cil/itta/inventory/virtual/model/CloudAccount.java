package com.uniti.cil.itta.inventory.virtual.model;

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
 * Cloud Account - Virtual and Cloud domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_cloud_account",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_cloud_account_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_cloud_account_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_cloud_account_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_cloud_account_ext", columnList = "external_ref")
        })
public class CloudAccount extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = true, length = 40)
    private CloudAccountProvider provider;

    @Column(name = "account_ref", nullable = false, length = 255)
    private String accountRef;

    @Column(name = "subscription_name", nullable = true, length = 255)
    private String subscriptionName;

    @Column(name = "monthly_budget", nullable = true, precision = 18, scale = 4)
    private BigDecimal monthlyBudget;

    @Column(name = "environment", nullable = true, length = 255)
    private String environment;

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
    private CloudAccountLifecycleStage lifecycleStage;

    public CloudAccount() {
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

    public CloudAccountProvider getProvider() { return provider; }
    public void setProvider(CloudAccountProvider provider) { this.provider = provider; }

    public String getAccountRef() { return accountRef; }
    public void setAccountRef(String accountRef) { this.accountRef = accountRef; }

    public String getSubscriptionName() { return subscriptionName; }
    public void setSubscriptionName(String subscriptionName) { this.subscriptionName = subscriptionName; }

    public BigDecimal getMonthlyBudget() { return monthlyBudget; }
    public void setMonthlyBudget(BigDecimal monthlyBudget) { this.monthlyBudget = monthlyBudget; }

    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public CloudAccountLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(CloudAccountLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CloudAccount other)) {
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
        return "CloudAccount{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "provider=" + provider + ", " +
                "accountRef=" + accountRef + ", " +
                "subscriptionName=" + subscriptionName + ", " +
                "monthlyBudget=" + monthlyBudget + ", " +
                "environment=" + environment + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + "}";
    }
}
