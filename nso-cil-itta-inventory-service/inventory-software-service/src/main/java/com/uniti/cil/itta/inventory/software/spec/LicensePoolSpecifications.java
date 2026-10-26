package com.uniti.cil.itta.inventory.software.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.LicensePoolSearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing LicensePool search. */
public final class LicensePoolSpecifications {

    private LicensePoolSpecifications() {
    }

    public static Specification<LicensePool> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<LicensePool> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<LicensePool> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<LicensePool> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicensePool> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicensePool> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicensePool> vendorIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("vendorId"), value);
    }

    public static Specification<LicensePool> poolNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("poolName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicensePool> totalSeatsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("totalSeats"), value);
    }

    public static Specification<LicensePool> totalSeatsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("totalSeats"), value);
    }

    public static Specification<LicensePool> usedSeatsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("usedSeats"), value);
    }

    public static Specification<LicensePool> usedSeatsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("usedSeats"), value);
    }

    public static Specification<LicensePool> renewalDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("renewalDate"), value);
    }

    public static Specification<LicensePool> renewalDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("renewalDate"), value);
    }

    public static Specification<LicensePool> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<LicensePool> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<LicensePool> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicensePool> lifecycleStageIn(List<LicensePoolLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<LicensePool> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicensePool> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<LicensePool> fromCriteria(String tenantId, LicensePoolSearchCriteria criteria) {
        Specification<LicensePool> spec = tenant(tenantId);
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
        if (criteria.getVendorId() != null) {
            spec = spec.and(vendorIdEquals(criteria.getVendorId()));
        }
        if (hasText(criteria.getPoolName())) {
            spec = spec.and(poolNameContains(criteria.getPoolName().trim()));
        }
        if (criteria.getTotalSeatsMin() != null) {
            spec = spec.and(totalSeatsMin(criteria.getTotalSeatsMin()));
        }
        if (criteria.getTotalSeatsMax() != null) {
            spec = spec.and(totalSeatsMax(criteria.getTotalSeatsMax()));
        }
        if (criteria.getUsedSeatsMin() != null) {
            spec = spec.and(usedSeatsMin(criteria.getUsedSeatsMin()));
        }
        if (criteria.getUsedSeatsMax() != null) {
            spec = spec.and(usedSeatsMax(criteria.getUsedSeatsMax()));
        }
        if (criteria.getRenewalDateFrom() != null) {
            spec = spec.and(renewalDateFrom(criteria.getRenewalDateFrom()));
        }
        if (criteria.getRenewalDateTo() != null) {
            spec = spec.and(renewalDateTo(criteria.getRenewalDateTo()));
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
