package com.uniti.cil.itta.inventory.radio.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.BaseStationSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing BaseStation search. */
public final class BaseStationSpecifications {

    private BaseStationSpecifications() {
    }

    public static Specification<BaseStation> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<BaseStation> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<BaseStation> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<BaseStation> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BaseStation> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BaseStation> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BaseStation> siteIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("siteId"), value);
    }

    public static Specification<BaseStation> enbIdMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("enbId"), value);
    }

    public static Specification<BaseStation> enbIdMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("enbId"), value);
    }

    public static Specification<BaseStation> technologyIn(List<BaseStationTechnology> values) {
        return (root, query, cb) -> root.get("technology").in(values);
    }

    public static Specification<BaseStation> sectorCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("sectorCount"), value);
    }

    public static Specification<BaseStation> sectorCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("sectorCount"), value);
    }

    public static Specification<BaseStation> cellCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("cellCount"), value);
    }

    public static Specification<BaseStation> cellCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("cellCount"), value);
    }

    public static Specification<BaseStation> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BaseStation> lifecycleStageIn(List<BaseStationLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<BaseStation> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BaseStation> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BaseStation> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<BaseStation> fromCriteria(String tenantId, BaseStationSearchCriteria criteria) {
        Specification<BaseStation> spec = tenant(tenantId);
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
        if (criteria.getSiteId() != null) {
            spec = spec.and(siteIdEquals(criteria.getSiteId()));
        }
        if (criteria.getEnbIdMin() != null) {
            spec = spec.and(enbIdMin(criteria.getEnbIdMin()));
        }
        if (criteria.getEnbIdMax() != null) {
            spec = spec.and(enbIdMax(criteria.getEnbIdMax()));
        }
        if (criteria.getTechnology() != null && !criteria.getTechnology().isEmpty()) {
            spec = spec.and(technologyIn(criteria.getTechnology()));
        }
        if (criteria.getSectorCountMin() != null) {
            spec = spec.and(sectorCountMin(criteria.getSectorCountMin()));
        }
        if (criteria.getSectorCountMax() != null) {
            spec = spec.and(sectorCountMax(criteria.getSectorCountMax()));
        }
        if (criteria.getCellCountMin() != null) {
            spec = spec.and(cellCountMin(criteria.getCellCountMin()));
        }
        if (criteria.getCellCountMax() != null) {
            spec = spec.and(cellCountMax(criteria.getCellCountMax()));
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
