package com.uniti.cil.itta.inventory.device.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.FanTraySearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing FanTray search. */
public final class FanTraySpecifications {

    private FanTraySpecifications() {
    }

    public static Specification<FanTray> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<FanTray> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<FanTray> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<FanTray> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FanTray> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FanTray> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FanTray> chassisIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("chassisId"), value);
    }

    public static Specification<FanTray> slotNumberMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("slotNumber"), value);
    }

    public static Specification<FanTray> slotNumberMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("slotNumber"), value);
    }

    public static Specification<FanTray> fanCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("fanCount"), value);
    }

    public static Specification<FanTray> fanCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("fanCount"), value);
    }

    public static Specification<FanTray> airflowIn(List<FanTrayAirflow> values) {
        return (root, query, cb) -> root.get("airflow").in(values);
    }

    public static Specification<FanTray> serialNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("serialNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FanTray> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<FanTray> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<FanTray> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<FanTray> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<FanTray> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FanTray> lifecycleStageIn(List<FanTrayLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<FanTray> fromCriteria(String tenantId, FanTraySearchCriteria criteria) {
        Specification<FanTray> spec = tenant(tenantId);
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
        if (criteria.getChassisId() != null) {
            spec = spec.and(chassisIdEquals(criteria.getChassisId()));
        }
        if (criteria.getSlotNumberMin() != null) {
            spec = spec.and(slotNumberMin(criteria.getSlotNumberMin()));
        }
        if (criteria.getSlotNumberMax() != null) {
            spec = spec.and(slotNumberMax(criteria.getSlotNumberMax()));
        }
        if (criteria.getFanCountMin() != null) {
            spec = spec.and(fanCountMin(criteria.getFanCountMin()));
        }
        if (criteria.getFanCountMax() != null) {
            spec = spec.and(fanCountMax(criteria.getFanCountMax()));
        }
        if (criteria.getAirflow() != null && !criteria.getAirflow().isEmpty()) {
            spec = spec.and(airflowIn(criteria.getAirflow()));
        }
        if (hasText(criteria.getSerialNumber())) {
            spec = spec.and(serialNumberContains(criteria.getSerialNumber().trim()));
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
