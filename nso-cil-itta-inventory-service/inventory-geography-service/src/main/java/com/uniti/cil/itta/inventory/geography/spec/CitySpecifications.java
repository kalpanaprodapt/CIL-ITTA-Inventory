package com.uniti.cil.itta.inventory.geography.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.CitySearchCriteria;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing City search. */
public final class CitySpecifications {

    private CitySpecifications() {
    }

    public static Specification<City> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<City> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<City> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<City> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<City> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<City> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<City> regionIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("regionId"), value);
    }

    public static Specification<City> postalPrefixContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("postalPrefix")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<City> populationMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("population"), value);
    }

    public static Specification<City> populationMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("population"), value);
    }

    public static Specification<City> latitudeMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("latitude"), value);
    }

    public static Specification<City> latitudeMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("latitude"), value);
    }

    public static Specification<City> longitudeMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("longitude"), value);
    }

    public static Specification<City> longitudeMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("longitude"), value);
    }

    public static Specification<City> metroAreaContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("metroArea")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<City> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<City> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<City> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<City> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<City> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<City> lifecycleStageIn(List<CityLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<City> fromCriteria(String tenantId, CitySearchCriteria criteria) {
        Specification<City> spec = tenant(tenantId);
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
        if (criteria.getRegionId() != null) {
            spec = spec.and(regionIdEquals(criteria.getRegionId()));
        }
        if (hasText(criteria.getPostalPrefix())) {
            spec = spec.and(postalPrefixContains(criteria.getPostalPrefix().trim()));
        }
        if (criteria.getPopulationMin() != null) {
            spec = spec.and(populationMin(criteria.getPopulationMin()));
        }
        if (criteria.getPopulationMax() != null) {
            spec = spec.and(populationMax(criteria.getPopulationMax()));
        }
        if (criteria.getLatitudeMin() != null) {
            spec = spec.and(latitudeMin(criteria.getLatitudeMin()));
        }
        if (criteria.getLatitudeMax() != null) {
            spec = spec.and(latitudeMax(criteria.getLatitudeMax()));
        }
        if (criteria.getLongitudeMin() != null) {
            spec = spec.and(longitudeMin(criteria.getLongitudeMin()));
        }
        if (criteria.getLongitudeMax() != null) {
            spec = spec.and(longitudeMax(criteria.getLongitudeMax()));
        }
        if (hasText(criteria.getMetroArea())) {
            spec = spec.and(metroAreaContains(criteria.getMetroArea().trim()));
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
