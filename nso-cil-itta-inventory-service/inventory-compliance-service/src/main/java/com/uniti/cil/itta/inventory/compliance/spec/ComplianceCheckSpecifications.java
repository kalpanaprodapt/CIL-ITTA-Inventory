package com.uniti.cil.itta.inventory.compliance.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.ComplianceCheckSearchCriteria;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ComplianceCheck search. */
public final class ComplianceCheckSpecifications {

    private ComplianceCheckSpecifications() {
    }

    public static Specification<ComplianceCheck> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ComplianceCheck> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ComplianceCheck> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ComplianceCheck> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceCheck> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceCheck> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceCheck> ruleIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("ruleId"), value);
    }

    public static Specification<ComplianceCheck> targetRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("targetRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceCheck> checkedAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("checkedAt"), value);
    }

    public static Specification<ComplianceCheck> checkedAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("checkedAt"), value);
    }

    public static Specification<ComplianceCheck> resultIn(List<ComplianceCheckResult> values) {
        return (root, query, cb) -> root.get("result").in(values);
    }

    public static Specification<ComplianceCheck> criticalityIn(List<ComplianceCheckCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<ComplianceCheck> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ComplianceCheck> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ComplianceCheck> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ComplianceCheck> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ComplianceCheck> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ComplianceCheck> fromCriteria(String tenantId, ComplianceCheckSearchCriteria criteria) {
        Specification<ComplianceCheck> spec = tenant(tenantId);
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
        if (criteria.getRuleId() != null) {
            spec = spec.and(ruleIdEquals(criteria.getRuleId()));
        }
        if (hasText(criteria.getTargetRef())) {
            spec = spec.and(targetRefContains(criteria.getTargetRef().trim()));
        }
        if (criteria.getCheckedAtFrom() != null) {
            spec = spec.and(checkedAtFrom(criteria.getCheckedAtFrom()));
        }
        if (criteria.getCheckedAtTo() != null) {
            spec = spec.and(checkedAtTo(criteria.getCheckedAtTo()));
        }
        if (criteria.getResult() != null && !criteria.getResult().isEmpty()) {
            spec = spec.and(resultIn(criteria.getResult()));
        }
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        if (hasText(criteria.getCostCenter())) {
            spec = spec.and(costCenterContains(criteria.getCostCenter().trim()));
        }
        if (criteria.getEffectiveFromFrom() != null) {
            spec = spec.and(effectiveFromFrom(criteria.getEffectiveFromFrom()));
        }
        if (criteria.getEffectiveFromTo() != null) {
            spec = spec.and(effectiveFromTo(criteria.getEffectiveFromTo()));
        }
        if (criteria.getEffectiveToFrom() != null) {
            spec = spec.and(effectiveToFrom(criteria.getEffectiveToFrom()));
        }
        if (criteria.getEffectiveToTo() != null) {
            spec = spec.and(effectiveToTo(criteria.getEffectiveToTo()));
        }
        return spec;
    }
}
