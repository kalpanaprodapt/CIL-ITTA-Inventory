package com.uniti.cil.itta.inventory.topology.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.TopologyNodeSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing TopologyNode search. */
public final class TopologyNodeSpecifications {

    private TopologyNodeSpecifications() {
    }

    public static Specification<TopologyNode> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<TopologyNode> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<TopologyNode> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<TopologyNode> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyNode> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyNode> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyNode> viewIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("viewId"), value);
    }

    public static Specification<TopologyNode> entityRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("entityRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyNode> nodeTypeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("nodeType")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyNode> xPosMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("xPos"), value);
    }

    public static Specification<TopologyNode> xPosMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("xPos"), value);
    }

    public static Specification<TopologyNode> yPosMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("yPos"), value);
    }

    public static Specification<TopologyNode> yPosMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("yPos"), value);
    }

    public static Specification<TopologyNode> layerMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("layer"), value);
    }

    public static Specification<TopologyNode> layerMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("layer"), value);
    }

    public static Specification<TopologyNode> criticalityIn(List<TopologyNodeCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<TopologyNode> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<TopologyNode> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<TopologyNode> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<TopologyNode> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<TopologyNode> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<TopologyNode> fromCriteria(String tenantId, TopologyNodeSearchCriteria criteria) {
        Specification<TopologyNode> spec = tenant(tenantId);
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
        if (hasText(criteria.getEntityRef())) {
            spec = spec.and(entityRefContains(criteria.getEntityRef().trim()));
        }
        if (hasText(criteria.getNodeType())) {
            spec = spec.and(nodeTypeContains(criteria.getNodeType().trim()));
        }
        if (criteria.getXPosMin() != null) {
            spec = spec.and(xPosMin(criteria.getXPosMin()));
        }
        if (criteria.getXPosMax() != null) {
            spec = spec.and(xPosMax(criteria.getXPosMax()));
        }
        if (criteria.getYPosMin() != null) {
            spec = spec.and(yPosMin(criteria.getYPosMin()));
        }
        if (criteria.getYPosMax() != null) {
            spec = spec.and(yPosMax(criteria.getYPosMax()));
        }
        if (criteria.getLayerMin() != null) {
            spec = spec.and(layerMin(criteria.getLayerMin()));
        }
        if (criteria.getLayerMax() != null) {
            spec = spec.and(layerMax(criteria.getLayerMax()));
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
