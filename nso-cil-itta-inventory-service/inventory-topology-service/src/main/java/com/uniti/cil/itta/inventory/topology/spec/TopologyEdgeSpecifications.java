package com.uniti.cil.itta.inventory.topology.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.TopologyEdgeSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing TopologyEdge search. */
public final class TopologyEdgeSpecifications {

    private TopologyEdgeSpecifications() {
    }

    public static Specification<TopologyEdge> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<TopologyEdge> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<TopologyEdge> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<TopologyEdge> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyEdge> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyEdge> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyEdge> viewIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("viewId"), value);
    }

    public static Specification<TopologyEdge> sourceNodeIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("sourceNodeId"), value);
    }

    public static Specification<TopologyEdge> targetNodeIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("targetNodeId"), value);
    }

    public static Specification<TopologyEdge> weightMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("weight"), value);
    }

    public static Specification<TopologyEdge> weightMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("weight"), value);
    }

    public static Specification<TopologyEdge> directedEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("directed"), value);
    }

    public static Specification<TopologyEdge> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<TopologyEdge> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<TopologyEdge> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<TopologyEdge> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<TopologyEdge> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyEdge> lifecycleStageIn(List<TopologyEdgeLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<TopologyEdge> fromCriteria(String tenantId, TopologyEdgeSearchCriteria criteria) {
        Specification<TopologyEdge> spec = tenant(tenantId);
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
        if (criteria.getViewId() != null) {
            spec = spec.and(viewIdEquals(criteria.getViewId()));
        }
        if (criteria.getSourceNodeId() != null) {
            spec = spec.and(sourceNodeIdEquals(criteria.getSourceNodeId()));
        }
        if (criteria.getTargetNodeId() != null) {
            spec = spec.and(targetNodeIdEquals(criteria.getTargetNodeId()));
        }
        if (criteria.getWeightMin() != null) {
            spec = spec.and(weightMin(criteria.getWeightMin()));
        }
        if (criteria.getWeightMax() != null) {
            spec = spec.and(weightMax(criteria.getWeightMax()));
        }
        if (criteria.getDirected() != null) {
            spec = spec.and(directedEquals(criteria.getDirected()));
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
