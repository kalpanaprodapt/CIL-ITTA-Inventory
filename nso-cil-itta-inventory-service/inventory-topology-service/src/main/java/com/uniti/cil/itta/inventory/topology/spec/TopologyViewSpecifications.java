package com.uniti.cil.itta.inventory.topology.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.TopologyViewSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing TopologyView search. */
public final class TopologyViewSpecifications {

    private TopologyViewSpecifications() {
    }

    public static Specification<TopologyView> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<TopologyView> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<TopologyView> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<TopologyView> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyView> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyView> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyView> viewNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("viewName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyView> viewTypeIn(List<TopologyViewViewType> values) {
        return (root, query, cb) -> root.get("viewType").in(values);
    }

    public static Specification<TopologyView> scopeFilterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("scopeFilter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyView> refreshedAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("refreshedAt"), value);
    }

    public static Specification<TopologyView> refreshedAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("refreshedAt"), value);
    }

    public static Specification<TopologyView> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<TopologyView> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<TopologyView> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyView> lifecycleStageIn(List<TopologyViewLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<TopologyView> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyView> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<TopologyView> fromCriteria(String tenantId, TopologyViewSearchCriteria criteria) {
        Specification<TopologyView> spec = tenant(tenantId);
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
        if (hasText(criteria.getViewName())) {
            spec = spec.and(viewNameContains(criteria.getViewName().trim()));
        }
        if (criteria.getViewType() != null && !criteria.getViewType().isEmpty()) {
            spec = spec.and(viewTypeIn(criteria.getViewType()));
        }
        if (hasText(criteria.getScopeFilter())) {
            spec = spec.and(scopeFilterContains(criteria.getScopeFilter().trim()));
        }
        if (criteria.getRefreshedAtFrom() != null) {
            spec = spec.and(refreshedAtFrom(criteria.getRefreshedAtFrom()));
        }
        if (criteria.getRefreshedAtTo() != null) {
            spec = spec.and(refreshedAtTo(criteria.getRefreshedAtTo()));
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
