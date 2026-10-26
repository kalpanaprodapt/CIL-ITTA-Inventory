package com.uniti.cil.itta.inventory.compliance.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.ComplianceRuleSearchCriteria;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ComplianceRule search. */
public final class ComplianceRuleSpecifications {

    private ComplianceRuleSpecifications() {
    }

    public static Specification<ComplianceRule> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ComplianceRule> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ComplianceRule> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ComplianceRule> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceRule> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceRule> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceRule> ruleCodeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("ruleCode")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceRule> frameworkIn(List<ComplianceRuleFramework> values) {
        return (root, query, cb) -> root.get("framework").in(values);
    }

    public static Specification<ComplianceRule> severityIn(List<ComplianceRuleSeverity> values) {
        return (root, query, cb) -> root.get("severity").in(values);
    }

    public static Specification<ComplianceRule> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceRule> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceRule> criticalityIn(List<ComplianceRuleCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<ComplianceRule> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ComplianceRule> fromCriteria(String tenantId, ComplianceRuleSearchCriteria criteria) {
        Specification<ComplianceRule> spec = tenant(tenantId);
        if (criteria == null) {
            return spec;
        }
        if (hasText(criteria.getQ())) {
            spec = spec.and(freeText(criteria.getQ().trim()));
        }
        if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
            spec = spec.and(statusIn(criteria.getStatus()));
        }
        if (hasText(criteria.getCode())) {
            spec = spec.and(codeContains(criteria.getCode().trim()));
        }
        if (hasText(criteria.getName())) {
            spec = spec.and(nameContains(criteria.getName().trim()));
        }
        if (hasText(criteria.getExternalRef())) {
            spec = spec.and(externalRefContains(criteria.getExternalRef().trim()));
        }
        if (hasText(criteria.getRuleCode())) {
            spec = spec.and(ruleCodeContains(criteria.getRuleCode().trim()));
        }
        if (criteria.getFramework() != null && !criteria.getFramework().isEmpty()) {
            spec = spec.and(frameworkIn(criteria.getFramework()));
        }
        if (criteria.getSeverity() != null && !criteria.getSeverity().isEmpty()) {
            spec = spec.and(severityIn(criteria.getSeverity()));
        }
        if (hasText(criteria.getRegion())) {
            spec = spec.and(regionContains(criteria.getRegion().trim()));
        }
        if (hasText(criteria.getOwner())) {
            spec = spec.and(ownerContains(criteria.getOwner().trim()));
        }
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        if (hasText(criteria.getCostCenter())) {
            spec = spec.and(costCenterContains(criteria.getCostCenter().trim()));
        }
        return spec;
    }
}
