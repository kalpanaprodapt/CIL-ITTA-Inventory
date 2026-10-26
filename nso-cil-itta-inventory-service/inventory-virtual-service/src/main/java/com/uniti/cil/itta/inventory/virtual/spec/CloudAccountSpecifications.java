package com.uniti.cil.itta.inventory.virtual.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.CloudAccountSearchCriteria;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing CloudAccount search. */
public final class CloudAccountSpecifications {

    private CloudAccountSpecifications() {
    }

    public static Specification<CloudAccount> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<CloudAccount> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<CloudAccount> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<CloudAccount> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CloudAccount> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CloudAccount> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CloudAccount> providerIn(List<CloudAccountProvider> values) {
        return (root, query, cb) -> root.get("provider").in(values);
    }

    public static Specification<CloudAccount> accountRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("accountRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CloudAccount> subscriptionNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("subscriptionName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CloudAccount> monthlyBudgetMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("monthlyBudget"), value);
    }

    public static Specification<CloudAccount> monthlyBudgetMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("monthlyBudget"), value);
    }

    public static Specification<CloudAccount> environmentContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("environment")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CloudAccount> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<CloudAccount> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<CloudAccount> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<CloudAccount> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<CloudAccount> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CloudAccount> lifecycleStageIn(List<CloudAccountLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<CloudAccount> fromCriteria(String tenantId, CloudAccountSearchCriteria criteria) {
        Specification<CloudAccount> spec = tenant(tenantId);
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
        if (criteria.getProvider() != null && !criteria.getProvider().isEmpty()) {
            spec = spec.and(providerIn(criteria.getProvider()));
        }
        if (hasText(criteria.getAccountRef())) {
            spec = spec.and(accountRefContains(criteria.getAccountRef().trim()));
        }
        if (hasText(criteria.getSubscriptionName())) {
            spec = spec.and(subscriptionNameContains(criteria.getSubscriptionName().trim()));
        }
        if (criteria.getMonthlyBudgetMin() != null) {
            spec = spec.and(monthlyBudgetMin(criteria.getMonthlyBudgetMin()));
        }
        if (criteria.getMonthlyBudgetMax() != null) {
            spec = spec.and(monthlyBudgetMax(criteria.getMonthlyBudgetMax()));
        }
        if (hasText(criteria.getEnvironment())) {
            spec = spec.and(environmentContains(criteria.getEnvironment().trim()));
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
        if (hasText(criteria.getLabels())) {
            spec = spec.and(labelsContains(criteria.getLabels().trim()));
        }
        if (criteria.getLifecycleStage() != null && !criteria.getLifecycleStage().isEmpty()) {
            spec = spec.and(lifecycleStageIn(criteria.getLifecycleStage()));
        }
        return spec;
    }
}
