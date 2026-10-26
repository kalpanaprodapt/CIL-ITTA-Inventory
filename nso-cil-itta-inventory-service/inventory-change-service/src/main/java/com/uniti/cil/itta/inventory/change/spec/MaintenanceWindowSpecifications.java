package com.uniti.cil.itta.inventory.change.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.MaintenanceWindowSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing MaintenanceWindow search. */
public final class MaintenanceWindowSpecifications {

    private MaintenanceWindowSpecifications() {
    }

    public static Specification<MaintenanceWindow> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<MaintenanceWindow> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<MaintenanceWindow> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<MaintenanceWindow> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MaintenanceWindow> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MaintenanceWindow> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MaintenanceWindow> windowNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("windowName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MaintenanceWindow> startsAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("startsAt"), value);
    }

    public static Specification<MaintenanceWindow> startsAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("startsAt"), value);
    }

    public static Specification<MaintenanceWindow> endsAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("endsAt"), value);
    }

    public static Specification<MaintenanceWindow> endsAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("endsAt"), value);
    }

    public static Specification<MaintenanceWindow> scopeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("scope")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MaintenanceWindow> impactLevelIn(List<MaintenanceWindowImpactLevel> values) {
        return (root, query, cb) -> root.get("impactLevel").in(values);
    }

    public static Specification<MaintenanceWindow> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<MaintenanceWindow> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<MaintenanceWindow> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MaintenanceWindow> lifecycleStageIn(List<MaintenanceWindowLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<MaintenanceWindow> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<MaintenanceWindow> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<MaintenanceWindow> fromCriteria(String tenantId, MaintenanceWindowSearchCriteria criteria) {
        Specification<MaintenanceWindow> spec = tenant(tenantId);
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
        if (hasText(criteria.getWindowName())) {
            spec = spec.and(windowNameContains(criteria.getWindowName().trim()));
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
        if (hasText(criteria.getScope())) {
            spec = spec.and(scopeContains(criteria.getScope().trim()));
        }
        if (criteria.getImpactLevel() != null && !criteria.getImpactLevel().isEmpty()) {
            spec = spec.and(impactLevelIn(criteria.getImpactLevel()));
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
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
        }
        if (hasText(criteria.getRegion())) {
            spec = spec.and(regionContains(criteria.getRegion().trim()));
        }
        return spec;
    }
}
