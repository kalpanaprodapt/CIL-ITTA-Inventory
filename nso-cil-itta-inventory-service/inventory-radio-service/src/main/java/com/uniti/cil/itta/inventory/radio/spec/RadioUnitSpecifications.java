package com.uniti.cil.itta.inventory.radio.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.RadioUnitSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing RadioUnit search. */
public final class RadioUnitSpecifications {

    private RadioUnitSpecifications() {
    }

    public static Specification<RadioUnit> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<RadioUnit> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<RadioUnit> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<RadioUnit> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RadioUnit> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RadioUnit> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RadioUnit> siteIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("siteId"), value);
    }

    public static Specification<RadioUnit> unitTypeIn(List<RadioUnitUnitType> values) {
        return (root, query, cb) -> root.get("unitType").in(values);
    }

    public static Specification<RadioUnit> bandMhzMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("bandMhz"), value);
    }

    public static Specification<RadioUnit> bandMhzMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("bandMhz"), value);
    }

    public static Specification<RadioUnit> txPowerDbmMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("txPowerDbm"), value);
    }

    public static Specification<RadioUnit> txPowerDbmMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("txPowerDbm"), value);
    }

    public static Specification<RadioUnit> serialNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("serialNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RadioUnit> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<RadioUnit> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<RadioUnit> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<RadioUnit> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<RadioUnit> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RadioUnit> lifecycleStageIn(List<RadioUnitLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<RadioUnit> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<RadioUnit> fromCriteria(String tenantId, RadioUnitSearchCriteria criteria) {
        Specification<RadioUnit> spec = tenant(tenantId);
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
        if (criteria.getUnitType() != null && !criteria.getUnitType().isEmpty()) {
            spec = spec.and(unitTypeIn(criteria.getUnitType()));
        }
        if (criteria.getBandMhzMin() != null) {
            spec = spec.and(bandMhzMin(criteria.getBandMhzMin()));
        }
        if (criteria.getBandMhzMax() != null) {
            spec = spec.and(bandMhzMax(criteria.getBandMhzMax()));
        }
        if (criteria.getTxPowerDbmMin() != null) {
            spec = spec.and(txPowerDbmMin(criteria.getTxPowerDbmMin()));
        }
        if (criteria.getTxPowerDbmMax() != null) {
            spec = spec.and(txPowerDbmMax(criteria.getTxPowerDbmMax()));
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
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
        }
        return spec;
    }
}
