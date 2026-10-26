package com.uniti.cil.itta.inventory.geography.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.SiteSearchCriteria;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Site search. */
public final class SiteSpecifications {

    private SiteSpecifications() {
    }

    public static Specification<Site> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Site> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Site> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Site> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Site> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Site> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Site> cityIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("cityId"), value);
    }

    public static Specification<Site> siteTypeIn(List<SiteSiteType> values) {
        return (root, query, cb) -> root.get("siteType").in(values);
    }

    public static Specification<Site> addressContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("address")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Site> latitudeMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("latitude"), value);
    }

    public static Specification<Site> latitudeMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("latitude"), value);
    }

    public static Specification<Site> longitudeMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("longitude"), value);
    }

    public static Specification<Site> longitudeMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("longitude"), value);
    }

    public static Specification<Site> powerKwMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("powerKw"), value);
    }

    public static Specification<Site> powerKwMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("powerKw"), value);
    }

    public static Specification<Site> accessHoursContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("accessHours")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Site> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<Site> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<Site> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Site> lifecycleStageIn(List<SiteLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Site> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Site> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Site> fromCriteria(String tenantId, SiteSearchCriteria criteria) {
        Specification<Site> spec = tenant(tenantId);
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
        if (criteria.getCityId() != null) {
            spec = spec.and(cityIdEquals(criteria.getCityId()));
        }
        if (criteria.getSiteType() != null && !criteria.getSiteType().isEmpty()) {
            spec = spec.and(siteTypeIn(criteria.getSiteType()));
        }
        if (hasText(criteria.getAddress())) {
            spec = spec.and(addressContains(criteria.getAddress().trim()));
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
        if (criteria.getPowerKwMin() != null) {
            spec = spec.and(powerKwMin(criteria.getPowerKwMin()));
        }
        if (criteria.getPowerKwMax() != null) {
            spec = spec.and(powerKwMax(criteria.getPowerKwMax()));
        }
        if (hasText(criteria.getAccessHours())) {
            spec = spec.and(accessHoursContains(criteria.getAccessHours().trim()));
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
