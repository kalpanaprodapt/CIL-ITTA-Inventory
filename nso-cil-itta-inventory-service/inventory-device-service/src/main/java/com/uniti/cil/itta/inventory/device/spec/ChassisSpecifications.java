package com.uniti.cil.itta.inventory.device.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.ChassisSearchCriteria;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Chassis search. */
public final class ChassisSpecifications {

    private ChassisSpecifications() {
    }

    public static Specification<Chassis> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Chassis> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Chassis> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Chassis> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Chassis> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Chassis> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Chassis> deviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("deviceId"), value);
    }

    public static Specification<Chassis> slotCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("slotCount"), value);
    }

    public static Specification<Chassis> slotCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("slotCount"), value);
    }

    public static Specification<Chassis> serialNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("serialNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Chassis> backplaneGbpsMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("backplaneGbps"), value);
    }

    public static Specification<Chassis> backplaneGbpsMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("backplaneGbps"), value);
    }

    public static Specification<Chassis> redundancyModeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("redundancyMode")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Chassis> lifecycleStageIn(List<ChassisLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Chassis> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Chassis> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Chassis> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Chassis> criticalityIn(List<ChassisCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Chassis> fromCriteria(String tenantId, ChassisSearchCriteria criteria) {
        Specification<Chassis> spec = tenant(tenantId);
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
        if (criteria.getDeviceId() != null) {
            spec = spec.and(deviceIdEquals(criteria.getDeviceId()));
        }
        if (criteria.getSlotCountMin() != null) {
            spec = spec.and(slotCountMin(criteria.getSlotCountMin()));
        }
        if (criteria.getSlotCountMax() != null) {
            spec = spec.and(slotCountMax(criteria.getSlotCountMax()));
        }
        if (hasText(criteria.getSerialNumber())) {
            spec = spec.and(serialNumberContains(criteria.getSerialNumber().trim()));
        }
        if (criteria.getBackplaneGbpsMin() != null) {
            spec = spec.and(backplaneGbpsMin(criteria.getBackplaneGbpsMin()));
        }
        if (criteria.getBackplaneGbpsMax() != null) {
            spec = spec.and(backplaneGbpsMax(criteria.getBackplaneGbpsMax()));
        }
        if (hasText(criteria.getRedundancyMode())) {
            spec = spec.and(redundancyModeContains(criteria.getRedundancyMode().trim()));
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
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        return spec;
    }
}
