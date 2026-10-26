package com.uniti.cil.itta.inventory.facility.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.RackSearchCriteria;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Rack search. */
public final class RackSpecifications {

    private RackSpecifications() {
    }

    public static Specification<Rack> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Rack> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Rack> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Rack> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Rack> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Rack> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Rack> roomIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("roomId"), value);
    }

    public static Specification<Rack> rackUnitsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("rackUnits"), value);
    }

    public static Specification<Rack> rackUnitsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("rackUnits"), value);
    }

    public static Specification<Rack> widthMmMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("widthMm"), value);
    }

    public static Specification<Rack> widthMmMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("widthMm"), value);
    }

    public static Specification<Rack> depthMmMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("depthMm"), value);
    }

    public static Specification<Rack> depthMmMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("depthMm"), value);
    }

    public static Specification<Rack> maxWeightKgMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("maxWeightKg"), value);
    }

    public static Specification<Rack> maxWeightKgMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("maxWeightKg"), value);
    }

    public static Specification<Rack> rowLabelContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("rowLabel")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Rack> positionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("position")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Rack> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Rack> lifecycleStageIn(List<RackLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Rack> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Rack> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Rack> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Rack> fromCriteria(String tenantId, RackSearchCriteria criteria) {
        Specification<Rack> spec = tenant(tenantId);
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
        if (criteria.getRoomId() != null) {
            spec = spec.and(roomIdEquals(criteria.getRoomId()));
        }
        if (criteria.getRackUnitsMin() != null) {
            spec = spec.and(rackUnitsMin(criteria.getRackUnitsMin()));
        }
        if (criteria.getRackUnitsMax() != null) {
            spec = spec.and(rackUnitsMax(criteria.getRackUnitsMax()));
        }
        if (criteria.getWidthMmMin() != null) {
            spec = spec.and(widthMmMin(criteria.getWidthMmMin()));
        }
        if (criteria.getWidthMmMax() != null) {
            spec = spec.and(widthMmMax(criteria.getWidthMmMax()));
        }
        if (criteria.getDepthMmMin() != null) {
            spec = spec.and(depthMmMin(criteria.getDepthMmMin()));
        }
        if (criteria.getDepthMmMax() != null) {
            spec = spec.and(depthMmMax(criteria.getDepthMmMax()));
        }
        if (criteria.getMaxWeightKgMin() != null) {
            spec = spec.and(maxWeightKgMin(criteria.getMaxWeightKgMin()));
        }
        if (criteria.getMaxWeightKgMax() != null) {
            spec = spec.and(maxWeightKgMax(criteria.getMaxWeightKgMax()));
        }
        if (hasText(criteria.getRowLabel())) {
            spec = spec.and(rowLabelContains(criteria.getRowLabel().trim()));
        }
        if (hasText(criteria.getPosition())) {
            spec = spec.and(positionContains(criteria.getPosition().trim()));
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
