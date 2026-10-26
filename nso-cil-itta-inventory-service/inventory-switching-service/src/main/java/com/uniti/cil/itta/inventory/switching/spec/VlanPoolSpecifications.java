package com.uniti.cil.itta.inventory.switching.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.VlanPoolSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing VlanPool search. */
public final class VlanPoolSpecifications {

    private VlanPoolSpecifications() {
    }

    public static Specification<VlanPool> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<VlanPool> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<VlanPool> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<VlanPool> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VlanPool> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VlanPool> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VlanPool> poolNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("poolName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VlanPool> rangeStartMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("rangeStart"), value);
    }

    public static Specification<VlanPool> rangeStartMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("rangeStart"), value);
    }

    public static Specification<VlanPool> rangeEndMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("rangeEnd"), value);
    }

    public static Specification<VlanPool> rangeEndMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("rangeEnd"), value);
    }

    public static Specification<VlanPool> allocatedMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("allocated"), value);
    }

    public static Specification<VlanPool> allocatedMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("allocated"), value);
    }

    public static Specification<VlanPool> scopeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("scope")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VlanPool> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<VlanPool> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<VlanPool> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VlanPool> lifecycleStageIn(List<VlanPoolLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<VlanPool> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<VlanPool> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<VlanPool> fromCriteria(String tenantId, VlanPoolSearchCriteria criteria) {
        Specification<VlanPool> spec = tenant(tenantId);
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
        if (hasText(criteria.getPoolName())) {
            spec = spec.and(poolNameContains(criteria.getPoolName().trim()));
        }
        if (criteria.getRangeStartMin() != null) {
            spec = spec.and(rangeStartMin(criteria.getRangeStartMin()));
        }
        if (criteria.getRangeStartMax() != null) {
            spec = spec.and(rangeStartMax(criteria.getRangeStartMax()));
        }
        if (criteria.getRangeEndMin() != null) {
            spec = spec.and(rangeEndMin(criteria.getRangeEndMin()));
        }
        if (criteria.getRangeEndMax() != null) {
            spec = spec.and(rangeEndMax(criteria.getRangeEndMax()));
        }
        if (criteria.getAllocatedMin() != null) {
            spec = spec.and(allocatedMin(criteria.getAllocatedMin()));
        }
        if (criteria.getAllocatedMax() != null) {
            spec = spec.and(allocatedMax(criteria.getAllocatedMax()));
        }
        if (hasText(criteria.getScope())) {
            spec = spec.and(scopeContains(criteria.getScope().trim()));
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
