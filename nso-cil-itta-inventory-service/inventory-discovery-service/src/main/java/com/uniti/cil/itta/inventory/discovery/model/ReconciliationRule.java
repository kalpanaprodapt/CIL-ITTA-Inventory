package com.uniti.cil.itta.inventory.discovery.model;

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
 * Reconciliation Rule - Discovery and Reconciliation domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_reconciliation_rule",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_reconciliation_rule_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_reconciliation_rule_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_reconciliation_rule_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_reconciliation_rule_ext", columnList = "external_ref")
        })
public class ReconciliationRule extends BaseEntity {

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

    @Column(name = "rule_name", nullable = false, length = 255)
    private String ruleName;

    @Column(name = "match_key", nullable = false, length = 255)
    private String matchKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = true, length = 40)
    private ReconciliationRuleAction action;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "expression", nullable = true, length = 4000)
    private String expression;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private ReconciliationRuleLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public ReconciliationRule() {
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

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getMatchKey() { return matchKey; }
    public void setMatchKey(String matchKey) { this.matchKey = matchKey; }

    public ReconciliationRuleAction getAction() { return action; }
    public void setAction(ReconciliationRuleAction action) { this.action = action; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public ReconciliationRuleLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(ReconciliationRuleLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

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
        if (!(o instanceof ReconciliationRule other)) {
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
        return "ReconciliationRule{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "ruleName=" + ruleName + ", " +
                "matchKey=" + matchKey + ", " +
                "action=" + action + ", " +
                "priority=" + priority + ", " +
                "expression=" + expression + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + "}";
    }
}
