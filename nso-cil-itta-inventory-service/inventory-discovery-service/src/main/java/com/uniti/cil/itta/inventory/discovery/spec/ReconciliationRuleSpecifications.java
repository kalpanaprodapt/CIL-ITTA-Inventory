package com.uniti.cil.itta.inventory.discovery.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.ReconciliationRuleSearchCriteria;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ReconciliationRule search. */
public final class ReconciliationRuleSpecifications {

    private ReconciliationRuleSpecifications() {
    }

    public static Specification<ReconciliationRule> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ReconciliationRule> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ReconciliationRule> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ReconciliationRule> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationRule> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationRule> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationRule> ruleNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("ruleName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationRule> matchKeyContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("matchKey")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationRule> actionIn(List<ReconciliationRuleAction> values) {
        return (root, query, cb) -> root.get("action").in(values);
    }

    public static Specification<ReconciliationRule> priorityMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("priority"), value);
    }

    public static Specification<ReconciliationRule> priorityMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("priority"), value);
    }

    public static Specification<ReconciliationRule> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationRule> lifecycleStageIn(List<ReconciliationRuleLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<ReconciliationRule> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationRule> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ReconciliationRule> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ReconciliationRule> fromCriteria(String tenantId, ReconciliationRuleSearchCriteria criteria) {
        Specification<ReconciliationRule> spec = tenant(tenantId);
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
        if (hasText(criteria.getMatchKey())) {
            spec = spec.and(matchKeyContains(criteria.getMatchKey().trim()));
        }
        if (criteria.getAction() != null && !criteria.getAction().isEmpty()) {
            spec = spec.and(actionIn(criteria.getAction()));
        }
        if (criteria.getPriorityMin() != null) {
            spec = spec.and(priorityMin(criteria.getPriorityMin()));
        }
        if (criteria.getPriorityMax() != null) {
            spec = spec.and(priorityMax(criteria.getPriorityMax()));
        }
        if (hasText(criteria.getLabels())) {
            spec = spec.and(labelsContains(criteria.getLabels().trim()));
        }
        if (criteria.getLifecycleStage() != null && !criteria.getLifecycleStage().isEmpty()) {
            spec = spec.and(lifecycleStageIn(criteria.getLifecycleStage()));
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
        return spec;
    }
}
