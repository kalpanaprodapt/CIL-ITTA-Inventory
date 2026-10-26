package com.uniti.cil.itta.inventory.vendor.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.HardwareModelSearchCriteria;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing HardwareModel search. */
public final class HardwareModelSpecifications {

    private HardwareModelSpecifications() {
    }

    public static Specification<HardwareModel> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<HardwareModel> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<HardwareModel> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<HardwareModel> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<HardwareModel> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<HardwareModel> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<HardwareModel> vendorIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("vendorId"), value);
    }

    public static Specification<HardwareModel> modelNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("modelNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<HardwareModel> formFactorContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("formFactor")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<HardwareModel> powerWattsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("powerWatts"), value);
    }

    public static Specification<HardwareModel> powerWattsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("powerWatts"), value);
    }

    public static Specification<HardwareModel> eolDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("eolDate"), value);
    }

    public static Specification<HardwareModel> eolDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("eolDate"), value);
    }

    public static Specification<HardwareModel> eosDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("eosDate"), value);
    }

    public static Specification<HardwareModel> eosDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("eosDate"), value);
    }

    public static Specification<HardwareModel> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<HardwareModel> lifecycleStageIn(List<HardwareModelLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<HardwareModel> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<HardwareModel> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<HardwareModel> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<HardwareModel> fromCriteria(String tenantId, HardwareModelSearchCriteria criteria) {
        Specification<HardwareModel> spec = tenant(tenantId);
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
        if (hasText(criteria.getModelNumber())) {
            spec = spec.and(modelNumberContains(criteria.getModelNumber().trim()));
        }
        if (hasText(criteria.getFormFactor())) {
            spec = spec.and(formFactorContains(criteria.getFormFactor().trim()));
        }
        if (criteria.getPowerWattsMin() != null) {
            spec = spec.and(powerWattsMin(criteria.getPowerWattsMin()));
        }
        if (criteria.getPowerWattsMax() != null) {
            spec = spec.and(powerWattsMax(criteria.getPowerWattsMax()));
        }
        if (criteria.getEolDateFrom() != null) {
            spec = spec.and(eolDateFrom(criteria.getEolDateFrom()));
        }
        if (criteria.getEolDateTo() != null) {
            spec = spec.and(eolDateTo(criteria.getEolDateTo()));
        }
        if (criteria.getEosDateFrom() != null) {
            spec = spec.and(eosDateFrom(criteria.getEosDateFrom()));
        }
        if (criteria.getEosDateTo() != null) {
            spec = spec.and(eosDateTo(criteria.getEosDateTo()));
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
