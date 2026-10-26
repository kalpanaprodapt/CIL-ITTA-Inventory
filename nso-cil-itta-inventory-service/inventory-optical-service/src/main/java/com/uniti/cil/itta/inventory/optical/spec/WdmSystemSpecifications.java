package com.uniti.cil.itta.inventory.optical.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.WdmSystemSearchCriteria;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing WdmSystem search. */
public final class WdmSystemSpecifications {

    private WdmSystemSpecifications() {
    }

    public static Specification<WdmSystem> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<WdmSystem> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<WdmSystem> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<WdmSystem> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WdmSystem> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WdmSystem> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WdmSystem> systemNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("systemName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WdmSystem> channelCapacityMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("channelCapacity"), value);
    }

    public static Specification<WdmSystem> channelCapacityMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("channelCapacity"), value);
    }

    public static Specification<WdmSystem> bandIn(List<WdmSystemBand> values) {
        return (root, query, cb) -> root.get("band").in(values);
    }

    public static Specification<WdmSystem> spanCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("spanCount"), value);
    }

    public static Specification<WdmSystem> spanCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("spanCount"), value);
    }

    public static Specification<WdmSystem> vendorIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("vendorId"), value);
    }

    public static Specification<WdmSystem> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<WdmSystem> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<WdmSystem> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<WdmSystem> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<WdmSystem> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<WdmSystem> lifecycleStageIn(List<WdmSystemLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<WdmSystem> fromCriteria(String tenantId, WdmSystemSearchCriteria criteria) {
        Specification<WdmSystem> spec = tenant(tenantId);
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
        if (hasText(criteria.getSystemName())) {
            spec = spec.and(systemNameContains(criteria.getSystemName().trim()));
        }
        if (criteria.getChannelCapacityMin() != null) {
            spec = spec.and(channelCapacityMin(criteria.getChannelCapacityMin()));
        }
        if (criteria.getChannelCapacityMax() != null) {
            spec = spec.and(channelCapacityMax(criteria.getChannelCapacityMax()));
        }
        if (criteria.getBand() != null && !criteria.getBand().isEmpty()) {
            spec = spec.and(bandIn(criteria.getBand()));
        }
        if (criteria.getSpanCountMin() != null) {
            spec = spec.and(spanCountMin(criteria.getSpanCountMin()));
        }
        if (criteria.getSpanCountMax() != null) {
            spec = spec.and(spanCountMax(criteria.getSpanCountMax()));
        }
        if (criteria.getVendorId() != null) {
            spec = spec.and(vendorIdEquals(criteria.getVendorId()));
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
