package com.uniti.cil.itta.inventory.integration.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.WebhookSubscriptionSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing WebhookSubscription search. */
public final class WebhookSubscriptionSpecifications {

    private WebhookSubscriptionSpecifications() {
    }

    public static Specification<WebhookSubscription> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<WebhookSubscription> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<WebhookSubscription> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<WebhookSubscription> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WebhookSubscription> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WebhookSubscription> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WebhookSubscription> callbackUrlContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("callbackUrl")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WebhookSubscription> eventTypesContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("eventTypes")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WebhookSubscription> secretRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("secretRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WebhookSubscription> activeEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("active"), value);
    }

    public static Specification<WebhookSubscription> retryLimitMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("retryLimit"), value);
    }

    public static Specification<WebhookSubscription> retryLimitMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("retryLimit"), value);
    }

    public static Specification<WebhookSubscription> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WebhookSubscription> lifecycleStageIn(List<WebhookSubscriptionLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<WebhookSubscription> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WebhookSubscription> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WebhookSubscription> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<WebhookSubscription> fromCriteria(String tenantId, WebhookSubscriptionSearchCriteria criteria) {
        Specification<WebhookSubscription> spec = tenant(tenantId);
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
        if (hasText(criteria.getCallbackUrl())) {
            spec = spec.and(callbackUrlContains(criteria.getCallbackUrl().trim()));
        }
        if (hasText(criteria.getEventTypes())) {
            spec = spec.and(eventTypesContains(criteria.getEventTypes().trim()));
        }
        if (hasText(criteria.getSecretRef())) {
            spec = spec.and(secretRefContains(criteria.getSecretRef().trim()));
        }
        if (criteria.getActive() != null) {
            spec = spec.and(activeEquals(criteria.getActive()));
        }
        if (criteria.getRetryLimitMin() != null) {
            spec = spec.and(retryLimitMin(criteria.getRetryLimitMin()));
        }
        if (criteria.getRetryLimitMax() != null) {
            spec = spec.and(retryLimitMax(criteria.getRetryLimitMax()));
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
