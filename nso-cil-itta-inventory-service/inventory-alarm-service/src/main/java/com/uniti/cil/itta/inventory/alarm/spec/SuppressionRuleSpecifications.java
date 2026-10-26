package com.uniti.cil.itta.inventory.alarm.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.SuppressionRuleSearchCriteria;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing SuppressionRule search. */
public final class SuppressionRuleSpecifications {

    private SuppressionRuleSpecifications() {
    }

    public static Specification<SuppressionRule> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<SuppressionRule> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<SuppressionRule> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<SuppressionRule> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SuppressionRule> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SuppressionRule> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SuppressionRule> ruleNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("ruleName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SuppressionRule> startsAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("startsAt"), value);
    }

    public static Specification<SuppressionRule> startsAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("startsAt"), value);
    }

    public static Specification<SuppressionRule> endsAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("endsAt"), value);
    }

    public static Specification<SuppressionRule> endsAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("endsAt"), value);
    }

    public static Specification<SuppressionRule> reasonContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("reason")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SuppressionRule> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SuppressionRule> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SuppressionRule> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SuppressionRule> criticalityIn(List<SuppressionRuleCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<SuppressionRule> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<SuppressionRule> fromCriteria(String tenantId, SuppressionRuleSearchCriteria criteria) {
        Specification<SuppressionRule> spec = tenant(tenantId);
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
        if (hasText(criteria.getRuleName())) {
            spec = spec.and(ruleNameContains(criteria.getRuleName().trim()));
        }
        if (criteria.getStartsAtFrom() != null) {
            spec = spec.and(startsAtFrom(criteria.getStartsAtFrom()));
        }
        if (criteria.getStartsAtTo() != null) {
            spec = spec.and(startsAtTo(criteria.getStartsAtTo()));
        }
        if (criteria.getEndsAtFrom() != null) {
            spec = spec.and(endsAtFrom(criteria.getEndsAtFrom()));
        }
        if (criteria.getEndsAtTo() != null) {
            spec = spec.and(endsAtTo(criteria.getEndsAtTo()));
        }
        if (hasText(criteria.getReason())) {
            spec = spec.and(reasonContains(criteria.getReason().trim()));
        }
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
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
